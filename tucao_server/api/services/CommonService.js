
module.exports = {

    convertCoordinate: function(i) { // 保留前四位
        var a = parseInt(i * 10000); 
        return a;
    },

    reConvertCoordinate: function(i) { // 整数转化为小数
        return i / 10000.0; 
    }
};
