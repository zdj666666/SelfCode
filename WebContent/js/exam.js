$(function(){
	//选择地区
		var getValue=document.getElementById("getTiao").innerHTML;
		if(getValue=="1"){
		$(".choose_area_title").click(function(){
			$(".choose_area_cont").slideToggle();
			$(".choose_subject_cont").slideUp();
			$(".choose_shcool_cont").slideUp();
			$('.choose_area_title img').toggleClass('on');
			$(".choose_shcool_title img").removeClass('on');
			$(".choose_subject_title img").removeClass('on');
		});
		$(".choose_area_cont li h4").click(function(){
			
			$(".choose_shcool_title").attr('disabled',false);
			$(".choose_shcool_cont").slideDown();
			$(".choose_area_cont").slideUp();
			$(".choose_area_title img").removeClass('on');
			$(".choose_shcool_title img").addClass('on');
			$(".choose_area_title h3").text($(this).text())
		});
		//选择学校
		$(".choose_shcool_title").click(function(){
			$(".choose_shcool_cont").slideToggle();
			$(".choose_area_cont").slideUp();
			$(".choose_subject_cont").slideUp();
			$('.choose_shcool_title img').toggleClass('on');
			$(".choose_area_title img").removeClass('on');
			$(".choose_subject_title img").removeClass('on');
		});
		$(".choose_shcool_cont li h3").click(function(){
			var getShowName=document.getElementById("getChooseName");
				getShowName.style.display="none";	
			var getShowFun=document.getElementById("chooseSub");
				getShowFun.style.display="none";
			$(".choose_subject_title").attr('disabled',false);
			$(".choose_subject_cont").slideDown();
			$(".choose_shcool_cont").slideUp();
			$(".choose_shcool_title img").removeClass('on');
			$(".choose_subject_title img").addClass('on');
			$(".choose_shcool_title h3").text($(this).text())
		});
		//选择科目
		$(".choose_subject_title").click(function(){
			var getShowName=document.getElementById("getChooseName");
				getShowName.style.display="none";
			$(".choose_subject_cont").slideToggle();
			$(".choose_shcool_cont").slideUp();
			$(".choose_area_cont").slideUp();
			$('.choose_subject_title img').toggleClass('on');
			$(".choose_area_title img").removeClass('on');
			$(".choose_shcool_title img").removeClass('on')
		});
		$(".choose_subject_cont li h4").click(function(){
			var getShowName=document.getElementById("getChooseName");
				getShowName.style.display="none";
			$(".choose_subject_cont").slideUp();
			$(".choose_subject_title img").removeClass('on');
			$(".choose_subject_cont li h4").text($(this).text())
		});
		}else{
			/*��ʾ��Ŀ������*/
			$(".choose_shcool_title").attr('disabled',false);
			$(".choose_shcool_cont").slideDown();
			$(".choose_area_cont").slideUp();
			$(".choose_area_title img").removeClass('on');
			$(".choose_shcool_title img").addClass('on');
			$(".choose_area_title h3").text($(this).text());
			
			$(".choose_subject_title").attr('disabled',false);
			$(".choose_subject_cont").slideDown();
			$(".choose_shcool_cont").slideUp();
			$(".choose_shcool_title img").removeClass('on');
			$(".choose_subject_title img").addClass('on');
			$(".choose_shcool_title h3").text($(this).text());
			
			$(".choose_area_title").click(function(){
				var getShowName=document.getElementById("getChooseName");
					getShowName.style.display="none";	
				$(".choose_area_cont").slideToggle();
				$(".choose_subject_cont").slideUp();
				$(".choose_shcool_cont").slideUp();
				$('.choose_area_title img').toggleClass('on');
				$(".choose_shcool_title img").removeClass('on');
				$(".choose_subject_title img").removeClass('on');
			});
			$(".choose_area_cont li h4").click(function(){
				$(".choose_shcool_title").attr('disabled',false);
				$(".choose_shcool_cont").slideDown();
				$(".choose_area_cont").slideUp();
				$(".choose_area_title img").removeClass('on');
				$(".choose_shcool_title img").addClass('on');
				$(".choose_area_title h3").text($(this).text())
			});
			//选择学校
			$(".choose_shcool_title").click(function(){
				var getShowName=document.getElementById("getChooseName");
					getShowName.style.display="none";	
				var getShowFun=document.getElementById("chooseSub");
					getShowFun.style.display="none";
				$(".choose_shcool_cont").slideToggle();
				$(".choose_area_cont").slideUp();
				$(".choose_subject_cont").slideUp();
				$('.choose_shcool_title img').toggleClass('on');
				$(".choose_area_title img").removeClass('on');
				$(".choose_subject_title img").removeClass('on');
			});
			$(".choose_shcool_cont li h3").click(function(){
				var getShowName=document.getElementById("getChooseName");
					getShowName.style.display="none";	
				$(".choose_subject_title").attr('disabled',false);
				$(".choose_subject_cont").slideDown();
				$(".choose_shcool_cont").slideUp();
				$(".choose_shcool_title img").removeClass('on');
				$(".choose_subject_title img").addClass('on');
				$(".choose_shcool_title h3").text($(this).text())
			});
			
			
		}
})
