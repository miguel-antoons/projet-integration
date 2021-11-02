from flask import Blueprint
from .database import db

signIn = Blueprint('signIn', __name__)


@signIn.route('/api/signIn', methods=['POST'])
def post_user():
    pass
    # TODO : add a user in db + tests

