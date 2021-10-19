from flask import Blueprint

signIn = Blueprint('signIn', __name__)


@signIn.route('', methods=['POST'])
def post_user():
    pass
    # TODO : add a user in db + tests

