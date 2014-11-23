function GoblinConfig(params) {
    this.values = params || defaults();
    function defaults() {
        return {
            username: 'anon',
            password: 'anon',
            url: 'http://localhost:8080/goblin/',
        }
    }
}

GoblinConfig.prototype.put = function (name, value) {
    this.values[name] = value;
};
GoblinConfig.prototype.get = function (name) {
    return this.values[name];
};
