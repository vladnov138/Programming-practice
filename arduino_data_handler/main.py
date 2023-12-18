import datetime
import logging
import sqlite3
import time

import zmq
from zmq import ZMQError


def create_connection(db_file):
    conn = None
    try:
        conn = sqlite3.connect(db_file)
    except Exception as e:
        print(e)
    return conn


def create_table(conn):
    cursor = conn.cursor()
    create_table_query = '''
        CREATE TABLE IF NOT EXISTS arduino_vals (
            type CHAR(1),
            value INTEGER,
            time DATETIME
        )
        '''
    cursor.execute(create_table_query)
    conn.commit()


def add_value(conn, task):
    sql = '''INSERT INTO arduino_vals(type, value, time)
              VALUES(?,?,?) '''
    cur = conn.cursor()
    cur.execute(sql, task)
    conn.commit()
    return cur.lastrowid


context = zmq.Context()


def connect_socket(ip):
    client = context.socket(zmq.SUB)
    client.connect(f"tcp://{ip}")
    client.subscribe('')
    return client


logger = logging.getLogger()
logging.basicConfig(filename='log.txt',
                    encoding='utf-8',
                    format='%(asctime)s - %(levelname)s - %(message)s',
                    datefmt='%m/%d/%Y %I:%M:%S %p',
                    level=logging.CRITICAL)

available_keys = "pmt"
database = "./data.db"


def main():
    connection = create_connection(database)
    with connection:
        create_table(connection)
        try:
            client = connect_socket("127.0.0.1:5555")
        except ZMQError:
            logger.critical("Unable to connect to socket. Trying to reconnect in 0.5 seconds")
            time.sleep(0.5)
            main()
        while True:
            try:
                buffer = client.recv_string(zmq.NOBLOCK)
            except zmq.error.Again:
                logger.critical("Buffer is unavailable. Trying again in 0.5 seconds.")
                time.sleep(0.5)
                continue
            message = buffer
            if len(message) == 1 and message in available_keys[1:]:
                message += " 1"
            print(f"Received message: {message}")
            kv = message.split()
            if message == '' or len(kv) < 2 or kv[0] not in available_keys:
                logger.critical(f"Invalid data format: {message}. Trying again in 0.5 seconds")
                time.sleep(0.5)
                continue
            cur_time = datetime.datetime.now(datetime.timezone.utc).isoformat()
            try:
                add_value(connection, (kv[0], kv[1], cur_time))
            except sqlite3.DatabaseError as e:
                logger.critical(f"Error adding value: {e}. KV: {kv[0]}, {kv[1]}. "
                                f"Trying again in 0.5 seconds")
                time.sleep(0.5)
                continue


if __name__ == '__main__':
    try:
        main()
    except KeyboardInterrupt:
        logger.critical(f"KeyboardInterrupt. Exiting...")
    except Exception as e:
        logger.critical(f"Unexpected exception: {e}")
