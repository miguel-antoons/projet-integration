import base64
import os

from flask import Flask
from flask_jwt_extended import JWTManager

from api import blueprints

# app need to be outside the function for testing
app = Flask(__name__)

# token manager
jwt_manager = JWTManager(app)
# JWT Config
app.config["JWT_SECRET_KEY"] = base64.urlsafe_b64decode(os.environ['JWT_SECRET_KEY'])




for blueprint in blueprints:
    app.register_blueprint(blueprint)



# Run debug mode
if __name__ == '__main__':
    app.run(debug=True)