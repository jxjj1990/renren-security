$(function () {
    $("#jqGrid").jqGrid({
        url: '../luversioninfo/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '版本号', name: 'version', index: 'version', width: 80 }, 			
			{ label: '安装包大小', name: 'packetsize', index: 'packetSize', width: 80 }, 			
			{ label: '通知图标', name: 'noticeiconuri', index: 'noticeIconUri', width: 80 }, 			
			{ label: 'apk图标', name: 'apkiconuri', index: 'apkIconUri', width: 80 }, 			
			{ label: 'apk地址', name: 'installuri', index: 'installUri', width: 80 }, 			
			{ label: '备注', name: 'desc', index: 'desc', width: 80 }			
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });


    new AjaxUpload('#uploadNotice', {
        action: '../luversioninfo/upload',
        name: 'file',
        autoSubmit:true,
        responseType:"json",
        onSubmit:function(file, extension){
            if (!(extension && /^(jpg|jpeg|png|gif)$/.test(extension.toLowerCase()))){
                alert('只支持jpg、jpeg、png、gif格式的图片！');
                return false;
            }
        },
        onComplete : function(file, r){
            if(r.code == 0){
                alert('上传成功');
                var noticeImg = document.getElementById("noticeImg");
                noticeImg.src = '/Images/' + r.fileName;
            }else{
                alert(r.msg);
            }
        }
    });

    new AjaxUpload('#uploadAppInstal', {
        action: '../luversioninfo/uploadApk',
        name: 'file',
        autoSubmit:true,
        responseType:"json",
        onSubmit:function(file, extension){
            if (!(extension && /^(apk)$/.test(extension.toLowerCase()))){
                alert('只支持APK文件');
                return false;
            }
        },
        onComplete : function(file, r){
            if(r.code == 0){
                alert('上传成功');
            }else{
                alert(r.msg);
            }
        }
    });

});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		luVersioninfo: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.luVersioninfo = {};
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
			var url = vm.luVersioninfo.id == null ? "../luversioninfo/save" : "../luversioninfo/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.luVersioninfo),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		del: function (event) {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: "../luversioninfo/delete",
				    data: JSON.stringify(ids),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								$("#jqGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		getInfo: function(id){
			$.get("../luversioninfo/info/"+id, function(r){
                vm.luVersioninfo = r.luVersioninfo;
            });
		},
		reload: function (event) {
		    //删除图片
            var noticeImg = document.getElementById("noticeImg");
            noticeImg.src = '/Images/nophoto.jpg';

            vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		}
	}
});