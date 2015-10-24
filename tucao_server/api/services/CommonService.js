
module.exports = {

    convertCoordinate: function(i) { //
        var a = parseInt(i * 1000000); 
        return a;
    },

    reConvertCoordinate: function(i) { // 整数转化为小数
        return i / 1000000.0; 
    }
};
