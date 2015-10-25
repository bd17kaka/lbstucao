/**
 * MsgController
 *
 * @description :: Server-side logic for managing msgs
 * @help        :: See http://sailsjs.org/#!/documentation/concepts/Controllers
 */


module.exports = {

    add: function (req, res) {
        var x = Number(req.param("x") || 0.0);
        var y = Number(req.param("y") || 0.0);
        var content = req.param("content") || "test";

        console.log("add,x="+x+",y="+y+","+content);

        Msg.create({
            x: CommonService.convertCoordinate(x),
            y: CommonService.convertCoordinate(y),
            content: content
        }).exec(function(err, msg) {
            return res.ok(msg);
        });
    },	
    
    push: function (req, res) {
        var x = Number(req.param("x") || 0.0);
        var y = Number(req.param("y") || 0.0);

        var ty = y + Constants.XY_50M;
        var by = y - Constants.XY_50M;
        var lx = x - Constants.XY_50M;
        var rx = x + Constants.XY_50M;

        console.log(x+","+y+","+Constants.XY_50M+","+lx+","+rx+","+by+","+ty);

        var date = new Date();
        var dateMid = date.getTime() - 2 *  60 * 1000; // 一分钟
        var dateBefore = new Date(dateMid);

        Msg.find().where({
            x: {
                '>=': CommonService.convertCoordinate(lx),
                '<=': CommonService.convertCoordinate(rx)
            },
            y: {
                '>=': CommonService.convertCoordinate(by),
                '<=': CommonService.convertCoordinate(ty)
            },
            createdAt: {
                '>=': dateBefore
            }
        }).exec(function(err, msgs) {
            return res.json(msgs);
        });
    },
    
    listExactly: function(req, res) {
        var x = Number(req.param("x") || 0.0);
        var y = Number(req.param("y") || 0.0);

        MsgService.listExactly(x, y, function(msgs) {
            console.log(msgs);
            var result = {};
            result.flag = true; result.contents= [];
            if (msgs && msgs.length >= 1) {
                result.x = CommonService.reConvertCoordinate(msgs[0].x);
                result.y = CommonService.reConvertCoordinate(msgs[0].y);
                for (var i in msgs) {
                    result.contents.push(msgs[i].content);
                }   
            }
            return res.json(result);
        });
    },
    
    count2: function(req, res) {

        var x = Number(req.param("x") || 0.0);
        var y = Number(req.param("y") || 0.0);

        MsgService.count(x, y, function(count) {
            var result = {};
            result.flag = true; result.count = count;
            if (count > 0) {
                MsgService.list(x, y, function(msgs) {
                    if (msgs && msgs.length >= 1) {
                        result.example = {
                            "x": CommonService.reConvertCoordinate(msgs[0].x),
                            "y": CommonService.reConvertCoordinate(msgs[0].y),
                            "content": msgs[0].content
                        };
                    }
                    return res.json(result);
                });
            } else {
                return res.json(result);        
            }
        });
    },

    count: function (req, res) {
        var x = Number(req.param("x") || 0.0);
        var y = Number(req.param("y") || 0.0);

        MsgService.list(x, y, function(msgs) {
            var result = {};
            result.flag = true; result.data = [];
            var tmp = {};
            if (msgs && msgs.length >= 1) {
                for (var i in msgs) {
                    var msg = msgs[i];
                    var key = msg.x + ":" + msg.y;
                    if (tmp[key] == undefined) {
                        tmp[key] = [];
                    }
                    tmp[key].push(msg.content);
                }
                for (var key in tmp) {
                    var tokens = key.split(":");
                    result.data.push({
                        "x": CommonService.reConvertCoordinate(tokens[0]),
                        "y": CommonService.reConvertCoordinate(tokens[1]),
                        "example": tmp[key][0],
                        "count": tmp[key].length
                    })
                } 
            }
            return res.json(result);
        });
    }
};

