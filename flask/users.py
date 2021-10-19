from flask import Blueprint


getUsers = Blueprint('getUsers', __name__)


@getUsers.route('', methods=['GET'])
def get_all_users():
    pass

