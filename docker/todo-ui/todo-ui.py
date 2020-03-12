import json
import bottle
import os
import collections
from pymongo import MongoClient
from bson.json_util import dumps
from bson import json_util
from bottle import route, run, template
from bson.objectid import ObjectId
from json2html import *

MONGO_HOST          = os.getenv('MONGO_HOST')
MONGO_PORT          = int(os.getenv('MONGO_PORT'))
MONGO_DATABASE      = os.getenv('MONGO_DATABASE')
MONGO_COLLECTION    = os.getenv('MONGO_COLLECTION')
MONGO_USERNAME      = os.getenv('MONGO_USERNAME')
MONGO_PASSWORD      = os.getenv('MONGO_PASSWORD')
TODO_SERVICE_IP     = os.getenv('TODO_SERVICE_IP')
TODO_SERVICE_PORT   = int(os.getenv('TODO_SERVICE_PORT'))


def connectDB():
    client = MongoClient("mongodb://%s:%s@%s:%d/" % (MONGO_USERNAME,MONGO_PASSWORD,MONGO_HOST,MONGO_PORT))
    return client

def getTodoDict(request_body):
    todo = collections.OrderedDict()
    
    todo["_id"]        = ObjectId(request_body['_id'])
    todo["task"]       = request_body['task']
    todo["duedate"]    = request_body['duedate']
    todo["labels"]     = request_body['labels']
    todo["comments"]   = request_body['comments']

    return todo   

@route('/ui', method='GET')
def viewTodos():
    client = connectDB()
    db = client[MONGO_DATABASE]
    results = list(db[MONGO_COLLECTION].find({}))
    ordered_results = []
    for index in range(0, len(results)):
        ordered_results.append(getTodoDict(results[index]))   
    response = json.loads(json_util.dumps(ordered_results))
    client.close() 
    return template("{{!data}}", data=json2html.convert(json = response))

if __name__ == '__main__':
    run(host=TODO_SERVICE_IP, port=TODO_SERVICE_PORT, debug=True)  
