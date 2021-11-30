from .client import app_client
from .food import addFood, getFood, removeFood, modifyFood
from .hello_world import app_hw
from .signIn import signIn
from .users import getUsers
from .login import getUser
from .signUp import addUser




# each time a new blue print is registerd, it MUST be added too this file the same way the others are !!!
blueprints = [
    app_client,
    addFood,
    app_hw,
    signIn,
    getUsers,
    getUser,
    addUser,
    getFood,
    removeFood,
    modifyFood
]