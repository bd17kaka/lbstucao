
module.exports = {

    list: function(x ,y, cb) {
        
        var ty = y + Constants.XY_50M;
        var by = y - Constants.XY_50M;
        var lx = x - Constants.XY_50M;
        var rx = x + Constants.XY_50M;

        console.log(x+","+y+","+Constants.XY_50M+","+lx+","+rx+","+by+","+ty);
        
        Msg.find().where({
            x: {
                '>=': CommonService.convertCoordinate(lx),
                '<=': CommonService.convertCoordinate(rx)
            },
            y: {
                '>=': CommonService.convertCoordinate(by),
                '<=': CommonService.convertCoordinate(ty)
            }
        }).exec(function(err, msgs) {
            cb(msgs);
        });
    },

    listExactly: function(x, y, cb) {
        console.log(x+","+y);
        Msg.find().where({
            x: CommonService.convertCoordinate(x),
            y: CommonService.convertCoordinate(y)
        }).exec(function(err, msgs) {
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

