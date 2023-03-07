/* global bp */
/* global diagram */


class Node {
    constructor(type) {
        this.tag = type;
    }

    getNodeType() {
        return this.tag;
    }
}

class StartNode extends Node {
    constructor() {
        super('start');
    }
}

class IfElseNode extends Node {
    constructor(condition){
        super('if-else');
        this.condition = condition;
    }
}

class LoopNode extends Node {
    constructor(count){
        super('loop');
        this.count = count;
    }
}

class SyncNode extends Node {
    constructor(syncStmt){
        super('sync');
        this.syncStmt = syncStmt;
    }
}


function get_exec(node) {
    switch (node.getNodeType()) {
        case 'start':
            return function (token) {
                return {"": token};
            }
        case 'if-else':
            return function (token) {
                if(node.condition(token))
                    return {"then": token};
                else
                    return {"else": token};
            }
        case 'loop':
            return function (token) {
                if (!('count' in token)) {
                    token.count = 0;
                }
                else
                    token.count++;
                if (token.count < node.count)
                    return {"next": token};
                else
                    return {"after": token};
            }
            case 'sync':
                return function (token) {
                    const stmt = node.syncStmt(token);
                    token.last_event=sync(stmt);
                    return {"": token};
                }
    }
}

function runTokenInNode(node) {
    return function (token) {
        const exec = get_exec(node);
        const next = exec(token);
        for (const key in next) {
            for (const n in node.getEdges().key) {
                bthread(n.tag + " ", next.key, runTokenInNode(n));
            }
        }
    }
}

for (const node in diagram.getNodes()) {
    const tokens = diagram.getTokens(node);
    if (tokens.length > 0) {
        for (const token of tokens) {
            bp.registerBThread(node.tag ,token, runTokenInNode(node));
        }
    }
}
