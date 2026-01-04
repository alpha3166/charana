module alpha3166.charana.web {
	requires alpha3166.charana.core;
	requires spring.beans;
	requires spring.boot;
	requires spring.boot.autoconfigure;
	requires spring.context;
	requires spring.core;
	requires spring.web;
	opens alpha3166.charana.web to spring.beans, spring.core, spring.web;
}
