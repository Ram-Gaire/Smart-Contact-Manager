console.log("this is a script filr");

const toggleSidebar = () => {

	if ($(".sidebar").is(":visible")) {
		//true
		//band karna hai
		$(".sidebar").css("display", "none");
		$(".content").css("margin-left", "0%");
	} else {
		//false
		//show karna hai
		$(".sidebar").css("display", "block");
		$(".content").css("margin-left", "20%");

	}

};

/*const search = () => {



	let query = $("#search-input").val();

	if (query == "") {
		$(".search-result").hide();
	} else {
		//search
		console.log(query);

		//sending request to server
		let url = `http://localhost:8282/search/${query}`;
		fetch(url)
			.then((response) => {
				//console.log(text);
				return response.json();
			})
			.then((data) => {
				console.log(data);

				let text = `<div class='list-group'>`;

				data.forEach((contact) => {
					text += `<a href='#' class='list-group-item list-group-item-action'> ${contact.name} </a>`;
				});
				text += `</div>`;

				$(".search-result").html(text);
				$(".search-result").show();

			});
			

	}
};
*/

const search = () => {
    let query = $("#search-input").val();

    if (query == "") {
        $(".search-result").hide();
    } else {
        let url = `http://localhost:8282/search/${query}`;
        fetch(url)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                let text = `<div class='list-group'>`;
                data.forEach((contact) => {
                    text += `<a href='/user/contact/${contact.cId}' class='list-group-item list-group-item-action'> ${contact.name} </a>`;
                });
                text += `</div>`;
                $(".search-result").html(text);
                $(".search-result").show();
            })
            .catch(error => {
                console.error('There was a problem with your fetch operation:', error);
            });
            
            
    }
};



