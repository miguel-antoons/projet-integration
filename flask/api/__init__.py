from .food import app_food
from .hello_world import app_hw
from .users import getUsers
from .login import getUser
from .signUp import addUser
from .raspberry import app_raspberry




# each time a new blue print is registerd, it MUST be added too this file the same way the others are !!!
blueprints = [
    app_food,
    app_hw,
    getUsers,
    getUser,
    addUser,
    app_raspberry
]