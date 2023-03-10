/* global bp */ // <-- Turn off warnings

/**
 * Tests if `o` is a javascript Set
 * @param o
 * @returns true iff `o` is a Set
 */
function isJSSet(o) {
    try {
        Set.prototype.has.call(o); // throws if o is not an object or has no [[SetData]]
        return true;
    } catch (e) {
        return false;
    }
}

let __sync_decoration__ = {
    defaults: [
        function (stmt, syncData, isHot) {
            bp.log.info("here-base {0};{1};{2};{3}", bp.thread.name, stmt,syncData,isHot)
            return syncData ? bp.hot(isHot).sync(stmt, syncData) : bp.hot(isHot).sync(stmt);
        },
    ],
    decorators: function () { return bp.thread.data.syncDecorators },
    level: function () { return bp.thread.data.syncDecoratorsLevel },
    add: function(value,i) {
        let loc = i;
        if(typeof i==='undefined' || i === null){
            loc = this.level()
        }
        this.decorators().splice(loc, 0,value)
    },
    remove: function(i) {
        let loc = i;
        if (typeof i === 'undefined' || i === null) {
            loc = this.level()
        }
        this.decorators().splice(loc, 1)
    },
    incLevel: function () { bp.thread.data.syncDecoratorsLevel++ },
    decLevel: function () { bp.thread.data.syncDecoratorsLevel-- },
    decorator: function () { return this.decorators()[this.level()] },
    applyCurrent: function (stmt, syncData, isHot) { return this.decorator()(stmt, syncData, isHot) },
    applyNext: function (stmt, syncData, isHot) {
        this.incLevel()
        try {
            return this.applyCurrent(stmt, syncData, isHot)
        }finally {
            this.decLevel()
        }
    }
}

/**
 * Adds a new b-thread, with initialized data field, to the b-program.
 * @param {string} name name of new b-thread
 * @param {object} data the initial data for the b-thread. Optional.
 * @param {function} fn entry point of new b-thread
 * @returns nothing
 */
function bthread(name, data, fn) {
    if (!fn) {
        // data is missing, we were invoked with 2 args
        fn = data;
        data = {};

    }
    // for the token in the graph
    if(!data.token){
        data.token={};
    }
    // for the token in the graph
    if(!data.nodeId){
        data.nodeId= NaN;
    }

    if (!data.request) {
        data.request = [];
    }
    if (!data.waitFor) {
        data.waitFor = [];
    }
    if (!data.block) {
        data.block = [];
    }
    if (!data.interrupt) {
        data.interrupt = [];
    }
    if (!data.syncDecoratorsLevel) {
        data.syncDecoratorsLevel = 0;
    }

    bp.registerBThread(name, data, function () {
        if (!data.syncDecorators) {
            data.syncDecorators = __sync_decoration__.defaults;
        }

        //the node work
        fn();
    });
}

/**
 * Appends EventSet(s) to a sync statement
 * @param {SyncStatement} stmt the statement to be added
 * @param {string} field the name of the stmt field (waitFor, request, block, or interrupt
 * @param {EventSet} value the EventSet(s) to add
 * @private
 */
function __appendToStmtPart__(stmt, field, value) {
    if (typeof value === 'undefined' || value == null) {
        return;
    }
    if (stmt[field] === 'undefined' || stmt[field] == null) {
        stmt[field] = [];
    }
    if (!Array.isArray(stmt[field])) {
        stmt[field] = [stmt[field]];
    }
    if (!Array.isArray(value)) {
        value = [value];
    }
    //since everything is array, and we can assume non-nested arrays in request, then we always concat.
    stmt[field] = value.concat(stmt[field]);
}

/**
 * Enters a synchronization point. Honors the RWBI stack.
 *
 * Can also be used as `sync()`. This causes the b-thread to sync using only
 * its RWBI stack.
 *
 * @param {SyncStatement} stmt the statement to be added
 * @param {Object} [syncData] optional sync data
 * @param {Boolean} [isHot=false] whether the sync is hot or not.
 * @returns {BEvent} The selected event
 * FIXME currently this changes stmt, we might not want that.
 *
 */
// the function must not be const since it is overridden in context
function sync(stmt, syncData, isHot) {
    if (!stmt) {
        stmt = {};
    }
    if(!isHot) {
        isHot = false;
    }

    __appendToStmtPart__(stmt, 'request', bp.thread.data.request);
    __appendToStmtPart__(stmt, 'waitFor', bp.thread.data.waitFor);
    __appendToStmtPart__(stmt, 'block', bp.thread.data.block);
    __appendToStmtPart__(stmt, 'interrupt', bp.thread.data.interrupt);

    return __sync_decoration__.applyCurrent(stmt, syncData, isHot)
}

/**
 * Returns true iff the function has been called by a b-thread
 * @returns {boolean}
 */
function isInBThread() {
    try {
        let a = bp.thread.name;
        return true;
    } catch (ex) {
        return false;
    }
}

/**
 * If expectInBThread is true - check if the function was called by a b-thread and throw an Error if not.
 * If expectInBThread is false - check if the function was called from the global scope and throw an Error if not.
 * The exception message will include the caller function name
 * @param caller The caller function name
 * @param expectInBThread Expect the code to be called by a b-thread
 */
function testInBThread(caller, expectInBThread) {
    if (expectInBThread && !isInBThread())
        throw new Error(String("The function " + caller + " must be called by a b-thread"));
    if (!expectInBThread && isInBThread())
        throw new Error(String("The function " + caller + " must be called from the global scope"));
}

function Any(type) {
    return bp.EventSet('Any(' + type + ')', function (e) {
        return String(e.name) === String(type)
    })
}

function Event(name, data) {
    if (typeof data !== 'undefined' && data != null)
        return bp.Event(name, data)
    return bp.Event(name)
}