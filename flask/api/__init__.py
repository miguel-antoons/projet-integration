from .delete_account import app_account
from .food import app_food
from .hello_world import app_hw
from .users import getUsers
from .login import getUser
from .signUp import addUser
from .raspberry import app_raspberry
from .locations import app_location
from .environnement import envi




# each time a new blue print is registerd, it MUST be added too this file the same way the others are !!!
blueprints = [
    app_food,
    app_hw,
    getUsers,
    getUser,
    addUser,
    app_raspberry,
    app_location,
    app_account,
    envi
]