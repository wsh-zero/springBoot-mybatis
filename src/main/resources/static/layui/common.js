/** 全局方法 */
/**
 * 去掉最后一个字符
 * @param data
 * @returns {string | *}
 */
function remove_last_char(data) {
    return data.substring(0, data.length - 1);
}

/**
 * 去掉对象中value为空的属性
 * @param object
 */
function removeProperty(object) {
    for (var prop in object) {
        if (object[prop] === '') {
            delete object[prop]
        }
    }
}