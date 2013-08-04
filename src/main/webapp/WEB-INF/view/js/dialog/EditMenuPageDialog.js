/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var EditDialog = require('./EditDialog');
    var BaseContent = require('./BaseContent');

    var Content = BaseContent.extend({
        tmpl: require('./EditMenuPageDialog.handlebars'),

        getRenderData: function () {
            var data = BaseContent.prototype.getRenderData.apply(this, arguments);
            data.ordinals = this.dialog.options.menuPageCollection.map(function (item, index) {
                return index + 1;
            }, this);
            data.ordinals.push(data.ordinals.length + 1);
            return data;
        }

    });
    
    var EditMenuPageDialog = EditDialog.extend({
        headerSuffix: '菜单页',

        ContentType: Content,

        formEl: '.form-edit-menu-page',

        FormType: require('../form/EditMenuPageForm'),

        render: function () {
            if (this.options.menuPageCollection.length === 0) {
                this.options.menuPageCollection.fetch({
                    success: function () {
                        this.doRender();
                    }.bind(this)
                });
            } else {
                this.doRender();
            }
        },

        doRender: function () {
            EditDialog.prototype.render.apply(this, arguments);
        }
        
        
    });
    
    return EditMenuPageDialog;
    
});
