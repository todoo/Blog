requirejs.config({
	//baseUrl: "view",
    //By default load any module IDs from js/lib
    //except, if the module ID starts with "app",
    //load it from the js/app directory. paths
    //config is relative to the baseUrl, and
    //never includes a ".js" extension since
    //the paths config could be for a directory.
    paths: {
		'text': '../../lib/require/text',
        'durandal':'../../lib/durandal/js',
        'plugins' : '../../lib/durandal/js/plugins',
        'transitions' : '../../lib/durandal/js/transitions',
        'knockout': '../../lib/knockout/knockout-3.1.0',
        'bootstrap': '../../lib/bootstrap/js/bootstrap',
        'jquery': '../../lib/jquery/jquery-1.9.1',
        'tools': '../../lib/tools',
        'highcharts': '../../lib/highcharts',
        'ueditor': '../../lib/ueditor'
    },
	shim: {
        'bootstrap': {
            deps: ['jquery'],
            exports: 'jQuery'
        },
        'highcharts/highcharts.src': {
            deps: ['jquery'],
            exports: 'jQuery'
        },
        'highcharts/themes/gray': {
            deps: ['highcharts/highcharts.src'],
            exports: 'Highcharts'
        },
        'ueditor/ueditor.all': {
            exports: 'UE'
        },
        'ueditor/ueditor.config': {
        	deps: ['ueditor/ueditor.all'],
            exports: 'UEDITOR_CONFIG'
        },
        'ueditor/ueditor.parse': {
        	deps: ['ueditor/ueditor.all'],
            exports: 'uParse'
        }
    }
});

define(['durandal/system', 'durandal/app'],  function (system, app) {
    system.debug(true);

    app.title = 'Blog';

    app.configurePlugins({
        router: true,
        dialog: true,
        http: true
    });
	//setTimeout(function(){
		app.start().then(function () {
			app.setRoot('shell','entrance');
		});
	//},3000);
});