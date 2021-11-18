from .client import app_client
from .food import addFood, getFood
from .hello_world import app_hw
# from .login import
from .signIn import signIn
from .users import getUsers
from .login import login
from .signUp import addUser



# each time a new blue print is registerd, it MUST be added too this file the same way the others are !!!
blueprints = [
    app_client,
    addFood,
    app_hw,
    signIn,
    getUsers,
    login,
<<<<<<< HEAD
    addUser
=======
    getFood
>>>>>>> 8e64179 (ADD: Non functionnal refresh of the product list)
]