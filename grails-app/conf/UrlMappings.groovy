class UrlMappings {
    static mappings = {
      "/$controller/$action?/$id?"{
	      constraints {
			 // apply constraints here
		  }
	  }
      "/"(redirect: "/portal/landing")
	  "500"(view:'/error')
	}
}
