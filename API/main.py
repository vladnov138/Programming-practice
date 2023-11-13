import _socket

def recvall(sock, n):
    data = bytearray()
    while len(data) < n:
        packet = sock.recv(n - len(data))
        if not packet:
            return None
        data.extend(packet)
    return data


host = "84.237.21.36"
port = 5151

with _socket.socket(_socket.AF_INET, _socket.SOCK_STREAM) as sock:
    sock.connect((host, port))
    sock.send(b"get")
    bts = sock.recv(80004)
    print(len(bts))
