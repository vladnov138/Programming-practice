import json
from enum import Enum
import struct


class Alignment(Enum):
    HORIZONTAL = 1
    VERTICAL = 2


class Widget():

    def __init__(self, parent):
        self.parent = parent
        self.children = []
        if self.parent is not None:
            self.parent.add_child(self)

    def add_child(self, child: "Widget"):
        self.children.append(child)

    def __str__(self):
        return f"{self.__class__.__name__}{self.children}"

    def __repr__(self):
        return str(self)

    @classmethod
    def from_json(cls, data, parent=None):
        classname = list(data.keys())[0]
        prop = data[classname][0]
        root = None
        match classname:
            case 'Ma':
                root = cls(prop)
            case 'La':
                root = Layout(parent, prop)
            case 'Li':
                root = LineEdit(parent, prop)
            case 'Co':
                root = ComboBox(parent, prop)
        k = 1
        while k < len(data[classname]):
            root.from_json(data[classname][k], parent=root)
            k += 1
        return root

    def to_json(self):
        classname = self.__class__.__name__[:2]
        result = {classname: []}
        match classname:
            case 'La':
                result[classname].append(self.alignment.value)
            case 'Li':
                result[classname].append(self.max_length)
            case 'Co':
                items = [str(item) for item in self.items]
                result[classname].append(items)
            case 'Ma':
                result[classname].append(self.title)
        for child in self.children:
            result[classname].append(child.to_json())
        return result

class MainWindow(Widget):

    def __init__(self, title: str):
        super().__init__(None)
        self.title = title


class Layout(Widget):

    def __init__(self, parent, alignment: Alignment):
        super().__init__(parent)
        self.alignment = alignment


class LineEdit(Widget):

    def __init__(self, parent, max_length: int = 10):
        super().__init__(parent)
        self.max_length = max_length

class ComboBox(Widget):

    def __init__(self, parent, items):
        super().__init__(parent)
        self.items = items


app = MainWindow("Application")
layout1 = Layout(app, Alignment.HORIZONTAL)
layout2 = Layout(app, Alignment.VERTICAL)

edit1 = LineEdit(layout1, 20)
edit2 = LineEdit(layout1, 30)

box1 = ComboBox(layout2, [1, 2, 3, 4])
box2 = ComboBox(layout2, ["a", "b", "c"])

print(app)

bts = app.to_json()
print(f"JSON data length {len(json.dumps(bts))}")
print(bts)

new_app = MainWindow.from_json(bts)
print(new_app)

print(new_app.children[1].children[1].items)