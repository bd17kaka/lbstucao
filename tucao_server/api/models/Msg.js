/**
* Msg.js
*
* @description :: TODO: You might write a short summary of how this model works and what it represents here.
* @docs        :: http://sailsjs.org/#!documentation/models
*/

module.exports = {

  attributes: {
    x: {
        type: 'float',
        required: true
    },
    y: {
        type: 'float',
        required: true
    },
    content: {
        type: 'string',
        required: true
    }
  }
};

