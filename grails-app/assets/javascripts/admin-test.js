function runAdminTests(admin){
    admin.connect();
    
    QUnit.test("fetch admin page", function(){
       var adminPage = admin.getPage('admin', 'index');
       ok(admin.isValidResult(adminPage), "load admin page"); 
    });
}