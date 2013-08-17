/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseRouter = require('./BaseRouter');

    var StatRouter = BaseRouter.extend({
        routes: {
            '': 'showStat',
            'stat': 'showStat'
        },

        showStat: function () {
            this.page.trigger('showStat');
        }
    });
    
    return StatRouter;
    
});

