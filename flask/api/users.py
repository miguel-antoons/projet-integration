from flask import Blueprint
from .database import db, collection


getUsers = Blueprint('getUsers', __name__)


@getUsers.route('/api/users', methods=['GET'])
def get_all_users():
    pass

