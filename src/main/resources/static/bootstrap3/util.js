$.fn.serializeObject = function () {
            var obj = {};
            var count = 0;
            $.each(this.serializeArray(), function (i, o) {
                var n = o.name, v = o.value;
                count++;
                obj[n] = obj[n] === undefined ? v
                : $.isArray(obj[n]) ? obj[n].concat(v)
                : [obj[n], v];
            });
            return obj;
        };