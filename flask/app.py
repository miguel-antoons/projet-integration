from flask import Flask
from api import blueprints


def create_app():
    app = Flask(__name__)

    for blueprint in blueprints:
        app.register_blueprint(blueprint)

    return app


# Run debug mode
if __name__ == '__main__':
    app = create_app()
    app.run(debug=True)
