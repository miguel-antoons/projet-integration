from .client import app_client
from .hello_world import app_hw

# each time a new blue print is registerd, it MUST be added too this file the same way the others are !!!
blueprints = [
    app_client,
    app_hw
]