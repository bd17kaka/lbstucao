
module.exports = {

    list: function(x ,y, cb) {
        
        var ty = y + Constants.XY_50M;
        var by = y - Constants.XY_50M;
        var lx = x - Constants.XY_50M;
        var rx = x + Constants.XY_50M;

        console.log("list,x="+x+",y="+y+","+Constants.XY_50M+",lx="+lx+",rx="+rx+",by="+by+",ty="+ty);
        
        Msg.find().where({
            x: {
                '>=': CommonService.convertCoordinate(lx),
                '<=': CommonService.convertCoordinate(rx)
            },
            y: {
                '>=': CommonService.convertCoordinate(by),
                '<=': CommonService.convertCoordinate(ty)
            }
        }).sort({createdAt: 'desc'}).exec(function(err, msgs) {
            if (err) console.log(err);
            cb(msgs);
        });
    },

    listExactly: function(x, y, cb) {

        var ty = y + Constants.XY_FIX;
        var by = y - Constants.XY_FIX;
        var lx = x - Constants.XY_FIX;
        var rx = x + Constants.XY_FIX;

        console.log("listExactly,x="+x+",y="+y+","+Constants.XY_FIX+",lx="+lx+",rx="+rx+",by="+by+",ty="+ty);

        Msg.find().where({
            x: {
                '>=': CommonService.convertCoordinate(lx),
                '<=': CommonService.convertCoordinate(rx)
            },
            y: {
                '>=': CommonService.convertCoordinate(by),
                '<=': CommonService.convertCoordinate(ty)
            }
            //x: CommonService.convertCoordinate(x),
            //y: CommonService.convertCoordinate(y)
        }).sort({createdAt: 'desc'}).exec(function(err, msgs) {
            if (err) console.log(err);
            cb(msgs);
        });
    },

    count: function(x, y, cb) {
        
        var ty = y + Constants.XY_50M;
        var by = y - Constants.XY_50M;
        var lx = x - Constants.XY_50M;
        var rx = x + Constants.XY_50M;

        console.log(x+","+y+","+Constants.XY_50M+","+lx+","+rx+","+by+","+ty);
    
        Msg.count({
            x: {
                '>=': CommonService.convertCoordinate(lx),
                '<=': CommonService.convertCoordinate(rx)
            },
            y: {
                '>=': CommonService.convertCoordinate(by),
                '<=': CommonService.convertCoordinate(ty)
            }
        }).exec(function(err, count) {
            cb(count);
        });
    },
};

