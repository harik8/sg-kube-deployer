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
MONGO_DATABASE      = os.getenv('MONGO_DB')
MONGO_COLLECTION    = os.getenv('MONGO_COLLECTION')
MONGO_USERNAME      = os.getenv('MONGO_USER')
MONGO_PASSWORD      = os.getenv('MONGO_PASSWORD')


def connectDB():
    client = MongoClient("mongodb://%s:%s@%s:%d/" % (MONGO_USER,MONGO_PASSWORD,MONGO_HOST,MONGO_PORT))
    return client

def getPassengerDict(request_body):
    passenger = collections.OrderedDict()
    
    passenger["_id"]                        = ObjectId(request_body['_id'])
    passenger["survived"]                   = bool(request_body['survived'])
    passenger["pclass"]                     = int(request_body['pclass'])
    passenger["name"]                       = request_body['name']
    passenger["sex"]                        = request_body['sex']
    passenger["age"]                        = int(request_body['age'])
    passenger["siblings_spouses_aboard"]    = int(request_body['siblings_spouses_aboard'])
    passenger["parents_children_aboard"]    = int(request_body['parents_children_aboard'])
    passenger["fare"]                       = float(request_body['fare'])

    return passenger   

@route('/ui', method='GET')
def viewPassengers():
    client = connectDB()
    db = client[MONGO_DB]
    results = list(db[MONGO_COLLECTION].find({}))
    ordered_results = []
    for index in range(0, len(results)):
        ordered_results.append(getPassengerDict(results[index]))   
    response = json.loads(json_util.dumps(ordered_results))
    client.close() 
    return template("{{!data}}", data=json2html.convert(json = response))

if __name__ == '__main__':
    run(host='0.0.0.0', port=9038, debug=True)  
