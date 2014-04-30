/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
  var $ = require('jquery');

  exports.createOrder = function(tableId, orderName) {
  // create order
    $.ajax({
        contentType: 'application/json',
        dataType: 'json',
        url:'/api/orders',
        type: 'POST',
        data: JSON.stringify({
            tableId: 1,
            dishes: [{id:1},{id:2}], name: "order1"
        }),
        success: function(data){
            console.log("create order succeeded");
        },
        error: function(){
            console.log("create order failed");
        }
    });
  }
}

