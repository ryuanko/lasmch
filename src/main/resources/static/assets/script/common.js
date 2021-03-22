var winW = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
var mobileW = 360;
var tabletW = 768;
var gnbW = 1366;

var isMobile = /Android|webOS|iPhone|iPad|iPod|BlackBerry/i.test(navigator.userAgent) ? true : false;

$(document).ready(function(){
	loadingOut();

	$(document).on('click',function() {
		if(isMobile) {
			$('body.scroll-lock, body.scroll-lock #gnb, body.scroll-lock .menu-full').css('width','100%');
		}
	});
	// gnb - pc : start
	var $body = $('html, body'),
		$gnb = $('#gnb'),
		$hoverMenu = $('.menu-full'),
		$gnbTit = $('.visual.title h2');
	$dimmed = $('.dimmed');


	// 마우스오버 시 펼침메뉴 열림
	/*$('.nav').on('click',function(){
		$gnb.addClass('hover');
		$('.depth2').show();
		if( !$(this).hasClass('search-input') ) {
			$hoverMenu.addClass('show');
		}
		$dimmed.show();
		$body.css('overflow','hidden');
		$('body').addClass('scroll-lock');
	});*/

	// gnb 영역 검색버튼 클릭시 검색영역 보임
	$('#gnb .search').on('click',function() {
		$gnb.addClass('search-input hover');
		//$('#gnb .search-wrap').fadeIn(300);
		$('#gnb .search-wrap').slideDown(200);
		$hoverMenu.removeClass('show');
		$dimmed.show();
		$body.css('overflow','hidden');
		$('body').addClass('scroll-lock');
	});

	// 펼침메뉴, 검색영역 닫기 ( 펼침메뉴와 검색영역이 동시에 열려있을 경우에는 펼침메뉴 먼저 닫힘)
	$('#gnb .menu-close').on('click',function() {
		if ( $gnb.hasClass('search-input') && $hoverMenu.hasClass('show') ) {
			$hoverMenu.removeClass('show');
			$body.css('overflow','hidden');
			$('body').addClass('scroll-lock');
		} else {
			$gnb.removeClass('search-input hover');
			$hoverMenu.removeClass('show');
			$('#gnb .search-wrap').slideUp(100);
			$dimmed.hide();
			$body.css('overflow','inherit');
			$('body').removeClass('scroll-lock');
			$('body').css('width','');
			$('.dimmed').css('z-index','5');
		}
	});

	/*if (winW > gnbW) {
		// 마우스아웃 시 펼침메뉴 닫힘
		$hoverMenu.on('mouseleave',function(e){
			var hoverTarget = e.relatedTarget;
			if( !$gnb.hasClass('search-input') && !(hoverTarget != null && hoverTarget.id == 'gnb') ) {
				$gnb.removeClass('hover');
				if( !$hoverMenu.hasClass('open') && !(hoverTarget != null && hoverTarget.id == 'gnb') ) {
					$body.css('overflow','inherit');
					$dimmed.hide();
					$('body').removeClass('scroll-lock');
					$('body').css('width','');
				}
			}
			if ( !(hoverTarget != null && hoverTarget.id == 'gnb') ){
				//$hoverMenu.removeClass('show');
			}
			if( $gnb.hasClass('search-input') && !$hoverMenu.hasClass('open') ){
				$hoverMenu.removeClass('show');

			}
		});
		$gnb.on('mouseleave',function(e){
			var hoverTarget = e.relatedTarget;
			if( hoverTarget == null ) {
				$body.css('overflow','inherit');
				$('body').removeClass('scroll-lock');
				$('body').css('width','');
			}
		});
	}*/

	// 마우스가 브라우저를 벗어날 경우 gnb 닫힘
	/*$(document).on("mouseleave", function(){
		if( !$hoverMenu.hasClass('open') ) {
			if ( !$gnb.hasClass('search-input') && $('.pop-layer:visible').length == 0){
				$gnb.removeClass('hover');
				$hoverMenu.removeClass('show');
				if ( window.outerWidth > 1366 ) {
					if( !$dimmed.hasClass('alert') ) {
						$dimmed.hide();
					}
				}
			}
			//if (  $gnb.hasClass('search-input') && $hoverMenu.hasClass('show') ){
			if (  $gnb.hasClass('search-input') ){
				$hoverMenu.removeClass('show');
				$body.css('overflow','hidden');
				$('body').addClass('scroll-lock');
			}
		}
	});*/
	// gnb - pc : end

	//gnb - tablet, mo : start
	$('.btn-menu').on('click',function(){
		$hoverMenu.addClass('open');
		$dimmed.fadeIn();
		$body.css('overflow-y','hidden');
		$('body').addClass('scroll-lock');
		$dimmed.css('z-index',20);
	});

	$('.menu-full .btn-close').on('click',function(){
		if ( $hoverMenu.hasClass('open') ) {
			$dimmed.hide();
			$body.css('overflow-y','inherit');
			$('body').removeClass('scroll-lock');
			$('body').css('width','');
			$dimmed.css('z-index',5);
			$hoverMenu.removeClass('open');
			$('.depth2').hide();
		} else {
			$('#gnb .menu-close').click();
		}
	});
	lnb();
	//gnb - tablet, mo : end

	// visual - slider
	visualSlider();
	topBanner();
	quickList();

	// top
	btnTop();

	// visual - scroll down
	$('.visual .scroll').on('click',function(){
		var visualH = $('.visual').innerHeight();
		$('html, body').animate({ scrollTop: visualH-100 }, 300, 'easeInOutQuad');
	});

	// main : tab-list
	tabList();
	tabSlider();


	youtubeTabList();
	// youtubeSlider();


	// 통합검색
	searchTabSlider();
	keyword();
	$('.search-cont div.sort button').on('click',function(){
		$('.search-category').addClass('show');
		$dimmed.show();
		$dimmed.css('z-index',10);
		$body.css('overflow','hidden');
		$('body').addClass('scroll-lock');
	});
	$('.search-category .close').on('click',function(){
		$('.search-category').removeClass('show');
		$dimmed.hide();
		$dimmed.css('z-index',5);
		$body.css('overflow-y','inherit');
		$('body').removeClass('scroll-lock');
	});


	var $calendar = $('.calendar, .calendar-from, .calendar-to');
	if( $(window).width() < 1440) {
		$calendar.attr('readonly','readonly')
		//$calendar.datepicker(  "option", "dateFormat", 'yy-mm-dd' );
	}

	// date picker
	$( ".calendar").datepicker({
		dateFormat: "yy-mm-dd",
		showOtherMonths: true,
		selectOtherMonths: true,
		monthNames: ['01','02','03','04','05','06','07','08','09','10','11','12'], //달력의 월 부분 Tooltip 텍스트
		dayNamesMin: ['S','M','T','W','T','F','S'] ,//달력의 요일 부분 텍스트
		showMonthAfterYear: true,
		yearSuffix: ".",
		showButtonPanel: true,
		closeText: 'Clear',
		beforeShow: function(input) {
			setTimeout(function () {
				var buttonPane = $(input)
					.datepicker("widget")
					.find(".ui-datepicker-buttonpane");

				var btn = $('<button class="ui-datepicker-clear ui-state-default ui-priority-secondary ui-corner-all" type="button">Clear</button>');
				btn.off("click").on("click", function () {
					input.value = '';
				});
				btn.appendTo(buttonPane);
			}, 1);

			if ($(window).width() < 767) {
				return { numberOfMonths: 1 };
			} else {
				return { numberOfMonths: 2 };
			}
		}
	});

	// layer-popup
	$('.btn.example, .btn-popup').on('click',function(){
		var $href = $(this).attr('data-href');
		layer_popup('#'+$href);
		$dimmed.addClass('pop');
		$('.dimmed').css('z-index','10');

		layerPos();

	});

	function layer_popup(el){
		var $el = $(el);
		$dimmed.fadeIn();
		$el.fadeIn();
		$body.css('overflow','hidden');
		$('body').addClass('scroll-lock');

		var $elWidth = ~~($el.outerWidth()),
			$elHeight = ~~($el.outerHeight()),
			docWidth = $(document).width(),
			docHeight = $(document).height();

		// 화면의 중앙에 레이어를 띄운다.
		if ($elHeight < docHeight || $elWidth < docWidth) {
			$el.css({
				marginTop: -$elHeight /2,
				//marginLeft: -$elWidth/2
			})
		} else {
			$el.css({top: 0, left: 0});
		}

		$el.find('.pop-close, .close, .pop-close-type2').click(function(){
			$dimmed.fadeOut();
			$dimmed.removeClass('pop');
			$el.fadeOut();
			$body.css('overflow','inherit');
			$('body').removeClass('scroll-lock');
			$('body').css('width','');
			$('.dimmed').css('z-index','5');
			return false;
		});

		/*$dimmed.on('click',function(){
			if( !$(this).hasClass('alert') ){
				$el.fadeOut();
				$(this).fadeOut();
				$body.css('overflow','inherit');
			}
		});*/
	}

	// mo: quick-link
	$(".quick-link").slick({
		slide: 'li',
		slidesToShow: 1,
		slidesToScroll: 1,
		dots: false,
		variableWidth: true,
		infinite: false,
		prevArrow: false,
		nextArrow: false
	});

	var $width = $(window).width();
	$(window).resize(function(){

		// visual - slider
		quickList();

		// main : tab-list
		tabList();

		youtubeTabList();

		/* tooltip */
		$('.tooltip-menu').removeClass('show');

		//$('.cont-slide').slick('resize');
		setTimeout(function () { $('.cont-slide').slick('setPosition'); }, 600);

		/* education calendar */
		var calendarH = $('.tbl-calendar table').height();
		$('.tbl-calendar').height(calendarH);
		$('.tbl-calendar').removeClass('hide');
		$('.calendar-info').removeClass('scroll');

		if( $width != $(window).width()) {
			// resize 될 때 lnb가 닫힘
			//if( $hoverMenu.hasClass('open') && $(window).width() > 1440) {
			if( $hoverMenu.hasClass('open') || $hoverMenu.hasClass('show') ) {
				$('.menu-full .btn-close').click();
			}


			if( $('.chkinput-wrap .tooltip').hasClass('show') ) {
				$('.chkinput-wrap .tooltip').removeClass('show');
				$('.content-wrap').height('auto');
				$('.content-wrap').css('overflow','inherit');
				$('.slick-list').css('overflow','hidden');
			}


			/* faq */
			var keywordH = $('.keyword').innerHeight();
			if( window.innerWidth > 767 ){
				$('.faq-keyword').css('height','auto');
			} else {
				$('.faq-keyword').css('height',48);
				$('.faq-keyword').removeClass('show');
			}

			layerPos();

			var briefingH = $('.briefing-menu').outerHeight();
			if( window.innerWidth > 767 ){
				$('.briefing-tab').css('height','auto');
			} else {
				$('.briefing-tab').css('height',briefingH);
			}
		}

		if( $(window).width() < 768) {
			$('.tbl-wrap .tbl-toggle tr').css('display','block');
			$('.tbl-wrap .tbl-toggle tr').css('display','none');
			$('.tbl-wrap .tbl-toggle').eq(0).addClass('open').find('tr').show();
		} else {
			$('.tbl-wrap .tbl-toggle tr').css('display','table-row');
		}
		//tblToggle();

		//filter
		if( window.outerWidth > 1366) {
			$('.search-box, .cont-search').removeClass('open');
			$('.cont-search .bottom-filter').hide();
			if(	$('.bottom-filter, .search-category').hasClass('show') ) {
				$('.bottom-filter, .search-category').removeClass('show');
				$('.dimmed').hide();
				$('body').removeClass('scroll-lock');
				$('body').css('width','');
			}
		} else {
			$('.search-box, .cont-search').removeClass('open');
			$('.pc-filter').hide();
			if ( $('.cont-search .bottom-filter:visible').length == 0) {
				$('.cont-search .bottom-filter').show();
			}
		}

		//auth-list, category-list
		if( $(window).width() > 1366) {
			$('.auth-list td, .cate-wrap ul').css('display','block');
			$('.auth-list tr, .cate-wrap').removeClass('close');
		}

		//통합검색
		searchTabSlider();
		keyword();

	});

	$(window).scroll(function(){
		gnbFixed();

		var scrollT = $(window).scrollTop(),
			scrollArea = $( document ).height();
		if ((scrollT + $( window ).height() ) >= scrollArea-160) {
			$('.btn-top').fadeIn(200);
			$('.btn-write').addClass('bottom');
		} else {
			$('.btn-top').fadeOut(200);
			$('.btn-write').removeClass('bottom');
		}
		btnTop();


	});

	$('.user').on('click',function() {
		$(this).siblings('.tooltip-menu').toggleClass('show');
	});

	$('.slider .slick-arrow').on('click',function() {
		$('.slider .slick-arrow').removeClass('on');
		$(this).addClass('on');
	});

	// search-box filter button
	$('.search-box .btn-filter').on('click',function() {
		var $box = $(this).parents('.search-box');
		if ( !$(this).hasClass('pc') && !$(this).hasClass('ta')){
			$dimmed.show();
			$dimmed.css('z-index',10);
			$box.find('.bottom-filter').addClass('show');
			$body.css('overflow','hidden');
			$('body').addClass('scroll-lock');
		} else if( $(this).hasClass('ta') ) {
			$dimmed.show();
			$dimmed.css('z-index',10);
			$box.find('.bottom-filter').addClass('show');
			$body.css('overflow','hidden');
			$('body').addClass('scroll-lock');
		}else {
			if ( !$(this).parents('.search-box').hasClass('open')){
				$box.find('.pc-filter').slideDown(200);
				$box.addClass('open');
			} else {
				$box.find('.pc-filter').slideUp(50);
				setTimeout(function () { $box.removeClass('open')}, 40);
			}
		}
	});
	$('.bottom-filter .bottom-close').on('click',function() {
		var $layerFilter = $(this).parent('.bottom-filter');
		$layerFilter.removeClass('show');
		$dimmed.hide();
		$dimmed.css('z-index',5);
		$body.css('overflow','inherit');
		$('body').removeClass('scroll-lock');
		$('body').css('width','');
	});

	// cont-search filter button
	$('.cont-search .btn-filter').on('click',function() {
		var $box = $(this).parents('.cont-search');
		var $filter = $(this).parents('.cont-search').find('.bottom-filter');
		if( window.outerWidth > 1366 ) {
			$filter.slideToggle(200);
			$box.toggleClass('open');
		} else {
			$filter.addClass('show');
			$dimmed.show();
			$dimmed.css('z-index',10);
			$body.css('overflow','hidden');
			$('body').addClass('scroll-lock');
		}
	});

	//board-list 페이지의 content-wrap 에 class추가
	if( $('.board-list').length > 0 || $('.pagenate').length > 0) {
		$('.content-wrap').addClass('in-list');
	}

	//board-view 페이지의 편집버튼이 있을경우 컨텐츠 영역 여백 조정
	$('.view-cont').each(function(){
		if ( $(this).find('.board-btns').length != 0 ){
			$(this).css('padding-bottom',140);
		}
	});

	//board view comment
	$('.comment-list').each(function(){
		var $input = $(this).find('.comment-input'),
			$edit = $(this).find('.btn-edit'),
			$modify = $(this).find('.btn-modify'),
			$cancel = $(this).find('.btn-cancel'),
			$reply= $(this).find('.btn-reply'),
			$recomment = $(this).find('.re-comment'),
			$list = $(this),
			$comment = $(this).find('.comment');

		$edit.on("click", function() {
			var $tool = $(this).siblings('.tool-edit');
			if ($tool.hasClass("active")) {
				$tool.removeClass("active");
			} else {
				$('.tool-edit').removeClass("active");
				$tool.addClass("active");
			}
		});

		$modify.on('click',function() {
			$(this).parent('.tool-edit').parent('.comment').addClass('modify');
			$(this).parent('.tool-edit').parent('.re-comment').addClass('modify');
			$(this).parent('.tool-edit').removeClass('active');
			$(this).parents('.re-comment').removeClass('in-writing');
			$list.removeClass('in-reinput');
		});
		$cancel.on('click',function() {
			//$(this).parents('.comment, .re-comment').removeClass('modify');
			$(this).parent('.comment, .re-comment').removeClass('modify');
		});
		$reply.on("click", function() {
			var $recommentR = $(this).parent('.re-comment'),
				$listR = $(this).parents('.comment-list'),
				$commentR = $(this).parents('.comment');

			if ($recommentR.hasClass("in-writing") || $listR.hasClass("in-reinput") || $commentR.hasClass("modify")) {
				if( $commentR.length != 0){
					$listR.removeClass('in-reinput');
				} else {
					$recommentR.removeClass('in-writing');
				}
			} else {
				$('.comment-list').removeClass('in-reinput');
				$('.re-comment').removeClass('in-writing');
				$('.comment').removeClass('modify');
				if( $(this).parents('.comment').length != 0){
					$listR.addClass('in-reinput');
				} else {
					$recommentR.addClass('in-writing');
				}
			}
		});
	});

	//faq
	$('.faq-keyword .keyword button').on('click',function() {
		$('.faq-keyword button').removeClass('on');
		$(this).addClass('on');
	});

	$('.faq-keyword .toggle').on('click',function(){
		var keywordH = $('.keyword').innerHeight();
		if ( $(window).width() < 768) {
			$(this).parent('.faq-keyword').toggleClass('show');
			if( $(this).parents('.faq-keyword').hasClass('show')) {
				$(this).parent('.faq-keyword').css('height',keywordH);

			} else {
				$(this).parent('.faq-keyword').css('height',48);
				$('.faq-keyword button').removeClass('on');
			}
		}
	});

	/* education calendar bottom bar */
	$('.full-calendar .toggle').on('click',function() {
		var calendarH = $('.tbl-calendar table').height();
		$(this).parent('.full-calendar').find('.tbl-calendar').toggleClass('hide');
		$(this).parents('.calendar-wrap').find('.calendar-info').toggleClass('scroll');
		if( $(this).parent('.full-calendar').find('.tbl-calendar').hasClass('hide') ) {
			$('.tbl-calendar').height(0);
		} else {
			$('.tbl-calendar').height(calendarH);
		}
	});

	$('.grade-caption .caption').on('click',function() {
		$('.grade-tooltip').toggleClass('show');
	});

	$('.eva-wrap a.caption').on('click',function() {
		$('.eva-wrap .noti-tooltip').toggleClass('show');
	});

	/* btn-dayoff */
	$('.btn-dayoff').on('click',function(){
		$('.btn-dayoff').removeClass('on');
		$(this).addClass('on');
	});

	if( $(window).width > 768) {
		$('.tbl-wrap .tbl-toggle tr').hide();
		$('.tbl-wrap .tbl-toggle').eq(0).addClass('open').find('tr').show();
	}
	tblToggle();

	$('.tbl-calendar td').on('click',function() {
		$('.tbl-calendar td button').removeClass('on');
		$(this).find('button').addClass('on');
	});

	//accordion
	aCurrent = 0;
	$('.accordion').each(function() {
		var $dt = $(this).children('dt');
		$dt.eq(0).addClass('open');
		$dt.eq(0).next('dd').show();

		if( $(this).hasClass('sugg-info') ) {
			$(this).find('dt').addClass('open');
			$(this).find('dd').show();
		}
		if( !$(this).hasClass('report-info') && !$(this).hasClass('sugg-info') ) {
			$dt.on('click',function() {
				var idx = $(this).index();
				if( aCurrent != idx){
					$dt.removeClass('open');
					$dt.next('dd').slideUp('fast');
					$(this).addClass('open');
					$(this).next('dd').slideDown('fast');
					aCurrent = idx;
				} else {
					$(this).toggleClass('open');
					$(this).next('dd').slideToggle('fast');
				}
			});
		} else {
			$dt.on('click',function(e) {
				if (e.target.tagName == 'BUTTON' || e.target.tagName == 'A' || e.target.tagName == 'LABEL' || e.target.tagName == 'INPUT' || e.target.className == 'radio-wrap')
					return;

				if( !$(this).hasClass('no-data') ) {
					$(this).toggleClass('open');
					$(this).next('dd').slideToggle('fast');
				}
			});
		}
	});

	$('.accordion dt').each(function(){
		if( $(this).next('dd').text() == '' ) {
			$(this).addClass('no-data');
		}
	});


	//scroll table tutorial
	$('.tutorial').on('click',function() {
		$(this).fadeOut();
	});

	//fly duty btns
	$('.flight-btns button').on('click',function() {
		$('.flight-btns button').removeClass('active');
		$(this).addClass('active');
	});

	//textarea autosize
	$('textarea').each(function () {
		if( $(this).parents('.comment-area').length == 0 ) {
			this.setAttribute('style', 'height:' + (this.scrollHeight) + 'px;');
		}
	}).on('input', function () {
		if( $(this).parents('.comment-area').length == 0 ) {
			this.style.height = 'auto';
			this.style.height = (this.scrollHeight) + 'px';
			if( $(this).parents('.write').length != 0 ) {
				$('.cont-slide').slick('setPosition');
			}
		}

	});

	//evaluation - star
	$('.eva-input.star').each(function(){
		$(this).find('input').on('click',function(){
			var idx = $(this).parent('li').index();
			$(this).parents('.eva-input.star').find('li').removeClass('on');
			for ( i= 0; i < idx ; i++ ){
				$(this).parents('.eva-input.star').find('li').eq(i).addClass('on');
			}
		});
	});

	$('.eva-result.star').each(function(){
		var eva = Number($(this).find('span').text());
		for ( i= 0; i < eva ; i++ ){
			$(this).find('li').eq(i).addClass('on');
		}
	});

	//checklist - tooltip
	$('.chkinput-wrap .q-wrap').each(function(){
		$(this).find('.accent2').parent().addClass('in-tooltip');
		$(this).find('.accent2').parent().append('<a href="#none">?</a>');

		$(this).parents('.content-wrap').find('.tab-menu li').on('click',function() {
			$('.slick-list').css('overflow','hidden');
			$('.content-wrap').height('auto');
			$('.content-wrap').css('overflow','inherit');
			$('.chkinput-wrap .tooltip').removeClass('show');
		});

	});

	$('.chkinput-wrap .in-tooltip > a').on('click',function() {
		if( $(this).parent().hasClass('q') ) {
			$(this).siblings('.tooltip').toggleClass('show');
		} else {
			$(this).parent('label').siblings('.tooltip').toggleClass('show');
		}

		if( window.outerWidth > 768 ) {
			var marginH = $('.tab-menu').height() + $('.form-list-wrap').height() + $('.caption-wrap').height() + $('.page-info').height() + $('.box-list').height();
			if( $('.chkinput-wrap').hasClass('write') ) {
				marginH = marginH + 290;
			} else {
				marginH = marginH + 399;
			}
			if( $(this).parents('.section').find('.tooltip').hasClass('show') ) {
				$('.slick-list').css('overflow','inherit');
				$('.content-wrap').height($(this).parents('.slick-list').height() + marginH);
				$('.content-wrap').css('overflow','hidden');
			} else {
				$('.slick-list').css('overflow','hidden');
				$('.content-wrap').height('auto');
				$('.content-wrap').css('overflow','inherit');
			}
		}

	});

	$('.chkinput-wrap .q-radio').each(function() {
		$(this).find('label').on('click',function() {
			if ( $(this).siblings('input').val() == 'f' ) {
				$(this).parents('.q-radio').find('.fail-list').show();
				$('.content-wrap').height('auto');
				$('.content-wrap').css('overflow','inherit');
			} else if ( $(this).siblings('input').val() == 'p' ) {
				$(this).parents('.q-radio').find('.fail-list').hide();
			} else if ( $(this).siblings('input').val() == 'n' ) {
				$(this).parents('.q-radio').find('.fail-list').hide();
			}
			$('.cont-slide').slick('setPosition');
		});

		if ( $(this).find('li').hasClass('checked') ){
			$(this).find('li.checked .fail-list input').attr('disabled','disabled');
		}
	});

	// evaluation, checklist - step slider
	contSlider();
	setTimeout(function () { $('.cont-slide').slick('setPosition'); }, 1000);

	// checklist - write
	$('.caption-wrap .noti').on('click',function(){
		$(this).next('ol').slideToggle('fast');
		$(this).parent('.caption-wrap').toggleClass('show');
	});

	//고충상담 radio-list
	$('.radio-list').each(function(){
		$(this).find('select').attr('disabled',true);
		$(this).find('input:checked + label + select').attr('disabled',false);
		$('.radio-list label').on('click',function() {
			$(this).parents('.radio-list').find('select').attr('disabled',true);
			$(this).siblings('select').attr('disabled',false);
		});
	});

	//doc mgmt - tab menu
	$('.briefing-tab .toggle').on('click',function(){
		var briefingH = $('.briefing-menu').outerHeight();
		if ( $(window).width() < 768) {
			if( $(this).parents('.briefing-tab').hasClass('close')) {
				$(this).parent('.briefing-tab').css('height',briefingH);
				$(this).parent('.briefing-tab').removeClass('close');
			} else {
				$(this).parent('.briefing-tab').css('height',50);
				$(this).parent('.briefing-tab').addClass('close');
			}
		}
	});

	//sys mgmt - my information tooltip
	$('.my-info .tooltip').on('click',function(){
		$(this).next('.tooltip-layer').toggleClass('show');
	});

	//sys mgmt - auth view table
	$('.auth-list .depth1').on('click',function(){
		if ( $(window).width() < 1366) {
			$(this).siblings('td').slideToggle('200');
			$(this).parent('tr').toggleClass('close');
		}

	});

	//sys mgmt - post pop
	$('.post .addr-input input').on('keyup',function(){
		if( $(this).val() != '') {
			$('.addr-input .del').show();
		} else {
			$('.addr-input .del').hide();
		}
	});
	$('.addr-input .del').on('click',function(){
		$(this).parent('.addr-input').find('input').val('');
		$(this).hide();
	});

	//category mgmt - category list
	$('.cate-tit').on('click',function(){
		if ( $(window).width() < 1366) {
			$(this).siblings('ul').slideToggle('200');
			$(this).parent('.cate-wrap').toggleClass('close');
		}
	});

	//통합검색 - 나의 검색어
	$('.page-search .search-set input').on('focusin',function(){
		$('.my-search').fadeIn(200);
		if(  $('.my-search:visible').length != 0	) {
			$('.page-search .detail-search .close').click();
		}
	});
	$('.my-search .close').on('click',function(){
		$('.my-search').fadeOut(200);
	});

	//통합검색 - 상세검색
	$('#detailchk').on('click',function(){
		var chk = $(this).is(":checked");//.attr('checked');
		if( chk ) {
			$('.detail-search').fadeIn(200);
		} else {
			$(this).next('label').click();
			$('.detail-search').fadeOut(200);
		}
	});
	$('.page-search .detail-search .close').on('click',function(){
		$('.detail-search').fadeOut(200);
		$('#detailchk').attr('checked',false);
	});

	//ALLERGEN INFO popup
	$('.pop-layer.allergen dt').on('click',function(){
		$('.pop-layer.allergen .pop-conts').scrollTop(0);
	});


});
// end: $(document).ready


//210122- Main Quick Menu
function quickMenu() {
	var menu_btutton = $('#quick-trigger');
	var menu_layer = $('#quick_menu_layer');
	var menu_close = $('#quick-closer');
	//열기
	menu_btutton.on('click', function() {
		$('.dimmed').fadeIn();
		$('.dimmed').css('z-index','99');
		menu_layer.addClass('active');
	});
	//닫기
	menu_close.on('click', function() {
		$('.dimmed').fadeOut();
		$('.dimmed').animate({
			'z-index': '5'
		});
		menu_layer.removeClass('active');
	});
}

// tab
$(function () {
	$('.tab-wrap').each(function () {
		var $tabCon = $(this).find('.tab-cont');
		var $tabMenu = $(this).find('.tab-menu li');

		if( !$(this).find('li').hasClass('active') ) {
			$(this).find('.tab-menu li').eq(0).addClass('active');
			$tabCon.hide();
			$tabCon.eq(0).show();
		}

		$tabMenu.click(function () {
			$(this).parents('.tab-wrap').find(".tab-menu li").removeClass("active");
			$(this).addClass("active");
			$(this).parents('.tab-wrap').find(".tab-cont").hide();

			var activeTab = $(this).attr("rel");
			$("#" + activeTab).fadeIn();
		});
	});
});

function lnb() {
	var $depth1 = $('.depth1 > li > a');

	current = -1;
	$depth1.on('click',function(){
		if ( $('.menu-full').hasClass('open') ) {

			var index = $depth1.index(this);
			if ( index != current ) {
				$depth1.removeClass('open');
				$depth1.siblings('.depth2').slideUp('300');
				$(this).addClass('open');
				$(this).siblings('.depth2').slideDown('500');
				current = index;
			} else {
				$(this).toggleClass('open');
				$(this).siblings('.depth2').slideToggle('500');
			}

		}
	});
}

// slider - paging
function pad(n, width) {
	n = n + '';
	return n.length >= width ? n : new Array(width - n.length + 1).join('0') + n;
}

// main : top-banner
function topBanner() {
	var $status = $('.paging');
	var $slickElement = $('.top-banner');

	$slickElement.on('init reInit afterChange', function (event, slick, currentSlide, nextSlide) {
		var i = (currentSlide ? currentSlide : 0) + 1;
		if ( slick.slideCount > 1) {
			$status.html('<strong>' + pad(i,1) + '</strong> <span class="slash">/</span> <span>' + pad(slick.slideCount,1)) + '</span>';
		} else {
			$('.top-banner .bnr-area').addClass('nopaging');
		}
	});

	$(".top-banner .bnr-area").slick({
		autoplay : true,
		autoplaySpeed : 5000,
		dots: true,
		slidesToShow: 1,
		slidesToScroll: 1,
		speed: 1000,
		responsive: [
			{
				breakpoint: 768,
				settings: {
					dots: false,
					vertical: true,
					//verticalSwiping: true,
				}
			}
		]
	});

	$('.top-banner .bnr-close button').on('click',function(){
		$('.top-banner').fadeOut(100);
	});
}

// main : visual - slider
function visualSlider() {
	var $status = $('.pagingInfo');
	var $slickElement = $('.slider');

	$slickElement.on('init reInit afterChange', function (event, slick, currentSlide, nextSlide) {
		var i = (currentSlide ? currentSlide : 0) + 1;
		$status.html('<strong>' + pad(i,2) + '</strong> <span class="slash">/</span> <span>' + pad(slick.slideCount,2)) + '</span>';
		if (slick.slideCount <=5) {
			$(this).find('.slick-list').css('padding-left',0);
		}
	});


	$(".visual .slider2").slick({
		autoplay : true,
		autoplaySpeed : 5000,
		// centerMode: true,
		// centerPadding:'24px',
		infinite: true,
		slidesToShow: 1,
		slidesToScroll: 1,
		arrows: true,
		speed: 1000,
		responsive: [
			{
				breakpoint: 1481,
				settings: {
					slidesToShow: 1,
					slidesToScroll: 1,
				}
			},
			{
				breakpoint: 1025,
				settings: {
					slidesToShow: 1,
				}
			},
			{
				breakpoint: 767,
				settings: {
					centerMode: true,
					centerPadding:'0',
					slidesToShow: 1,
				}
			},
			{
				breakpoint: 361,
				settings: {
					centerMode: true,
					centerPadding:'50px',
					slidesToShow: 1,
				}
			},
			{
				breakpoint: 321,
				settings: {
					centerMode: true,
					centerPadding:'30px',
					slidesToShow: 1,
				}
			}
		]
	});
}

// main : quick-list
$quickList = false;
function quickList(){
	if($(window).width() < 768){
		if(!$quickList){
			$(".quick-list").slick({
				slide: 'li',
				slidesToShow: 4,
				slidesToScroll: 4,
				dots: true,
			});
			$quickList = true;
		}
	} else if($(window).width() > 768){
		if( $quickList){
			$('.quick-list').slick('unslick');
			$quickList = false;
		}
	}
};

// input - file
/* var $fileBox = null;

$(function() {
	inputFile();
})

function inputFile() {
	$fileBox = $('.input-file');
	fileLoad();
}

function fileLoad() {
	$.each($fileBox, function(idx){
		var $this = $fileBox.eq(idx),
				$btnUpload = $this.find('[type="file"]'),
				$label = $this.find('.file-label');

		$btnUpload.on('change', function() {
			var $target = $(this),
					fileName = $target.val(),
					$fileText = $target.siblings('.file-name');
			$fileText.val(fileName);
		})

		$btnUpload.on('focusin focusout', function(e) {
			e.type == 'focusin' ?
				$label.addClass('file-focus') : $label.removeClass('file-focus');
		})

	})
}  */

// btn-top position
function btnTop() {
	var footerH = $('#footer').innerHeight();

	$('.btn-top').on('click',function(){
		$('html, body').stop().animate({ scrollTop: 0 }, 500, 'easeInOutQuad');
	});

	if ( $(window).width() < 767)	{
		$('.btn-top').css('bottom',40+'px');
	} else {
		$('.btn-top').css('bottom',footerH+24+'px');
	}
}

// main: tab-list
function tabList() {
	if ( $(window).width() < 768 ) {
		$('.section > li').hide();
		$('.section > li').eq(0).show();
		$('.section-title h3 a').on('click',function(){
			var idx = $(this).parents('li').attr('data-slick-index');
			$('.section-title a').removeClass('on');
			$(this).addClass('on');
			$('.section > li').hide();
			$('.section > li').eq(idx).show();
		});
	} else {
		$('.section > li').show();
	}
}

function tabSlider() {
	$(".section-title").slick({
		slide: 'li',
		slidesToShow: 4,
		slidesToScroll: 1,
		dots: false,
		variableWidth: true,
		infinite: false,
		prevArrow: false,
		nextArrow: false
	});
}

function youtubeTabList() {
	// if ( $(window).width() < 768 ) {
	// 	$('.youtube-section > li').hide();
	// 	$('.youtube-section > li').eq(0).show();
	// 	$('.youtube-section-title h3 a').on('click',function(){
	// 		var idx = $(this).parents('li').attr('data-slick-index');
	// 		$('.youtube-section-title a').removeClass('on');
	// 		$(this).addClass('on');
	// 		$('.youtube-section > li').hide();
	// 		$('.youtube-section > li').eq(idx).show();
	// 	});
	// } else {
	// 	$('.youtube-section > li').show();
	// 	$('.youtube-li').css('display','inline-block');
	// }
}

function youtubeSlider() {
	$(".youtube-section-title").slick({
		slide: 'li',
		slidesToShow: 2,
		slidesToScroll: 1,
		dots: false,
		variableWidth: true,
		infinite: false,
		prevArrow: false,
		nextArrow: false
	});
}


function gnbFixed() {
	var $gnb = $('#gnb'),
		$gnbTit = $('.visual.title h2');
	if ( $(window).scrollTop() > 10 ){
		$gnb.addClass('fixed');
		$gnbTit.addClass('fixed');
	} else {
		$gnb.removeClass('fixed');
		$gnbTit.removeClass('fixed');
	}
}

function tblToggle() {
	/* tbl-toggle */
	if ( $(window).width() < 768){
		// 210118 - 전체 펼쳐지도록 수정
		// $('.tbl-wrap .tbl-toggle tr').css('display','none');
		// $('.tbl-wrap .tbl-toggle').eq(0).addClass('open').find('tr').show();
		$('.tbl-wrap .tbl-toggle').addClass('open').find('tr').show();
	}
	$('.tbl-toggle caption').on('click',function() {
		if ( $(window).width() < 768){

			if ( !$(this).parent('.tbl-toggle').hasClass("open")) {
				$(this).parent('.tbl-toggle').addClass("open");
				$(this).parent('table').find('tr').slideDown(200);
			} else {
				$(this).parent('.tbl-toggle').removeClass("open");
				$(this).parent('table').find('tr').slideUp(200);
			}

		} else {
			return false;
		}
	});
}

function loading() {
	$('.loading').show();
	$('.dimmed').addClass('alert');
	$('.dimmed').show();
	$('.dimmed').css('z-index','50');
	$('body').css('overflow','hidden');
	$('body').addClass('scroll-lock');
}

function loadingOut() {
	$('.loading').hide();
	if ( !$('#gnb').hasClass('search-input') && $('.pop-layer:visible').length == 0 )	{
		$('.dimmed').removeClass('alert');
		$('.dimmed').hide();
		$('.dimmed').css('z-index','5');
		$('body').css('overflow','inherit');
		$('body').removeClass('scroll-lock');
		$('body').css('width','');
	} else if( $('#gnb').hasClass('search-input') ) {
		$('.dimmed').css('z-index','5');
	} else if( $('.pop-layer:visible').length != 0 ) {
		$('.dimmed').css('z-index','10');
	}
}

function contSlider() {
	var $status = $('.page-info p');
	var $slickElement = $('.cont-slide');
	var $slideWrap = $('.eva-wrap, .chkinput-wrap');
	var $writeBtn = $('.write .btn-wrap');

	$slickElement.on('init reInit afterChange', function (event, slick, currentSlide, nextSlide) {
		var i = (currentSlide ? currentSlide : 0) + 1;
		$status.html( i + '/' + slick.slideCount );

		$('.page-info ol li').removeClass('on');
		$('.page-info ol li').eq(i-1).addClass('on');
		$('.cont-slide .slick-prev').text('이전');
		$('.cont-slide .slick-next').text('다음');

		$writeBtn.hide();
		if ($slideWrap.hasClass('write') ) {
			if ( i == slick.slideCount) {
				$writeBtn.show();
				$slideWrap.css('padding-bottom',0);
			} else {
				$slideWrap.css('padding-bottom',80);
			}
		}

	});

	$('.cont-slide').slick({
		infinite: false,
		fade: true,
		speed: 0,
		adaptiveHeight: true,
		//draggable: false,
		swipe: false,
	});
	$('.cont-slide .slick-arrow').on('click',function(){
		var wrapT = $slideWrap.offset().top;
		$('html, body').animate({ scrollTop: wrapT - 100 }, 200, 'easeInOutQuad');
		$('.tooltip').removeClass('show');

		if( window.outerWidth > 768 ) {
			$('.slick-list').css('overflow','hidden');
			$('.content-wrap').height('auto');
			$('.content-wrap').css('overflow','inherit');
			//$('.slick-arrow').css('z-index',3);
		}
	});

}

function layerPos() {
	if( !(isMobile) ) {
		$('.pop-layer').css('left','calc(50% - 9px)');
		if($(window).width() < 768 && $('.pop-layer').hasClass('full')) {
			$('.pop-layer').css('left',0);
		}
		if( $(window).width() <= $('.pop-layer').width() ) {
			$('.pop-layer').css('left',0);
		}
	}

	$('.pop-layer').each(function() {
		if( window.outerWidth > 767 ) {
			if( $(this).find('.pop-conts').outerHeight() > 640 && !$(this).hasClass('max') && !$(this).hasClass('sm') ) {
				$(this).css('height',645);
				$(this).find('.pop-conts').css('padding-bottom',40);
			}
			if( $(this).hasClass('max') ) {
				$(this).find('.pop-conts').css('padding-bottom',40);
			}
		} else {
			if ( $(this).hasClass('full') ) {
				//$(this).css('height','100%');
			}
		}
	});

}

$searchTab = false;
function searchTabSlider(){
	if($(window).width() < 768){
		if(!$searchTab){
			$(".search-menu").slick({
				slide: 'li',
				//slidesToShow: 3,
				//slidesToScroll: 1,
				dots: false,
				variableWidth: true,
				infinite: false,
				prevArrow: false,
				nextArrow: false
			});
			$searchTab = true;
		}
	} else if($(window).width() > 768){
		if( $searchTab){
			$('.search-menu').slick('unslick');
			$searchTab = false;
		}
	}
};

$keyword = false;
function keyword(){
	if($(window).width() < 768){
		if(!$keyword){
			$('.search-keyword ul').slick({
				slide: 'li',
				//slidesToShow: 3,
				slidesToScroll: 1,
				dots: false,
				variableWidth: true,
				infinite: false,
				prevArrow: false,
				nextArrow: false
			});
			$keyword = true;
		}
	} else if($(window).width() > 768){
		if( $keyword){
			$('.search-keyword ul').slick('unslick');
			$keyword = false;
		}
	}
};

function customBtnClose() {

	var $body = $('html, body'),
		$hoverMenu = $('.menu-full'),
	$dimmed = $('.dimmed');

	if ( $hoverMenu.hasClass('open') ) {
		$dimmed.hide();
		$body.css('overflow-y','inherit');
		$('body').removeClass('scroll-lock');
		$('body').css('width','');
		$dimmed.css('z-index',5);
		$hoverMenu.removeClass('open');
		$('.depth2').hide();
	} else {
		$('#gnb .menu-close').click();
	}
}
