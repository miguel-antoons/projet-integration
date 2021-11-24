# -*- coding: utf-8 -*-
# Your code goes below this line

# Flask Library
from flask import Blueprint, request, jsonify, json
from flask import Flask
from flask_bcrypt import Bcrypt
from flask_jwt_extended import JWTManager
from flask_mail import Mail, Message
# Infos Database
from .database import db, users
# Library
import random
import os

from dotenv import load_dotenv

load_dotenv()

app = Flask(__name__)
mail = Mail(app)

# Mail Configuration

app.config['MAIL_SERVER'] = os.getenv('MAIL_SERVER')
app.config['MAIL_PORT'] = os.getenv('MAIL_PORT')
app.config['MAIL_USE_SSL'] = os.getenv('MAIL_USE_SSL')
app.config['MAIL_USERNAME'] = os.getenv('MAIL_USERNAME')
app.config['MAIL_PASSWORD'] = os.getenv('MAIL_PASSWORD')

mail = Mail(app)

getUsers = Blueprint('getUsers', __name__)

"""
*** API ROUTE FOR RESET PASSWORD  ***

 --- This route is the API to reset a user's password with his email address -- 

"""


@getUsers.route('/api/users/reset-password/checkemail/<email>', methods=['GET', 'PUT'])
def check_email(email):
    """[check_email]

    Args:
        email ([String]): [The user's email]

    Steps:

        1) Check the existence of the   
        2) Generates a random code of 6 numbers
        3) Adds the code in the database
        4)Sends an email with the code

    Returns:

       - [Data User]: [If the email exists the user information is returned]
       or
       - ['message': 'this email does not exist']: [If the email does not exist]

    """

    if request.method == 'GET':

        # Get email parameter
        # email_param = request.args.get('email')
        # print(email_param)

        # Checks in the database if the email exists
        email_exist = list(users.find({"Email": email}))
        # print(email_exist)

        if email_exist:

            # generate a random code with 6 number
            numbers = random.sample(range(10), 6)
            number_str = ''.join(map(str, numbers))
            # print(number_str)

            # Update the code via email
            users.find_one_and_update(
                {"Email": email},
                {"$set":
                     {"Code": number_str}
                 }, upsert=True

            )

            # Send Mail

            msg = Message('Hello de SmartFridge Teams!', sender='smart.fridge.teams@gmail.com', recipients=[email])
            msg.body = "Bonjour de l'Equipe SmartFridge, voici votre code :  " + number_str
            mail.send(msg)

            # check ID
            for user in email_exist:
                user.pop('_id')

            # Email data : json.dumps(email_exist)

            message = ['message: this email exist']
            return json.dumps(message)

        else:

            message = ['message: this email does not exist']
            return json.dumps(message)


"""
*** API ROUTE FOR Check code  ***

 --- This route is the API to check code with his email address -- 

"""


@getUsers.route('/api/users/reset-password/checkcode/<email>/<code>', methods=['GET', 'PUT'])
def check_code_email(email, code):
    if request.method == 'GET':

        # Find code key by email
        check_good_code = list(users.find({"Email": email}, {"Code": 1}))

        # loop element
        # delete ID
        for codevalue in check_good_code:
            codevalue.pop('_id')
            # print(codevalue)

        # get ggood code from email
        good_code = codevalue['Code']

        if code == good_code:

            message = ['The code is good']
            return json.dumps(message)

        else:

            message = ['code is false']
            return json.dumps(message)


"""
*** API ROUTE FOR UPDATE PASSWORD  ***

 --- This route is the API to UPDATE PASSWORD with his email address -- 

           
"""


@getUsers.route('/api/users/reset-password/update-password/<email>/<password>', methods=['GET', 'PUT'])
def update_password(email, password):
    if request.method == 'GET':
        # Check email address
        email_exist = list(users.find({"Email": email}))
        print(email_exist)

        if email_exist:
            return json.dumps("Bonsoir Paris")

        else:
            return "Email does not exist "

    elif request.method == 'PUT':

        # Check email address
        email_exist = list(users.find({"Email": email}))
        print(email_exist)

        if email_exist:

            # Update the code via email
            users.find_one_and_update(
                {"Email": email},
                {"$set":
                     {"Password": password}
                 }, upsert=True

            )

            return json.dumps(["Password Update IS ok"])
        else:
            return json.dumps(["Email does not exist "])


"""
Verification if the username already exist
"""


@getUsers.route('/api/users/<username>', methods=['GET'])
def username_exist(username):
    records = db.Users
    result = list(records.find({"Username": username}))

    for client in result:
        client.pop('_id')

    print(result)
    return json.dumps(result)
