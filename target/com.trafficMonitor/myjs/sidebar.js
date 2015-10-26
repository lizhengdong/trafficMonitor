function initSideBar() {
    var sidebar = '<li class="header">主导航栏</li>' +
        '<li class="active treeview">' +
        '<a href="#">' +
        '<i class="fa fa-dashboard"></i> <span>流量记录</span> ' +
        '<i class="fa fa-angle-left pull-right"></i>' +
        '</a>' +
        '<ul class="treeview-menu">' +
        '<li><a href="index.html"><i class="fa fa-circle-o"></i>当日上网流量记录</a></li>' +
        '<li class="active"><a href="index2.html"><i class="fa fa-circle-o"></i>实时上网流量记录</a></li>' +
        '</ul>' +
        '</li>' +
        '<li class="treeview">' +
        '<a href="#">' +
        '<i class="fa fa-files-o"></i>' +
        '<span>黑名单</span>' +
        '<span class="label label-primary pull-right">手动</span>' +
        '</a>' +
        '<ul class="treeview-menu">' +
        '<li><a href="pages/layout/top-nav.html"><i class="fa fa-circle-o"></i>黑名单设置</a></li>' +
        '</ul>' +
        '</li>' +
        '<li>' +
        '<a href="pages/widgets.html">' +
        '<i class="fa fa-th"></i> <span>运行状态</span>' +
        '<small class="label pull-right bg-green">实时</small>' +
        '</a>' +
        '</li>' +
        '<li class="treeview">' +
        '<a href="#">' +
        '<i class="fa fa-edit"></i>' +
        '<span>系统设置</span>' +
        '<i class="fa fa-angle-left pull-right"></i>' +
        '</a>' +
        '<ul class="treeview-menu">' +
        '<li><a href="pages/charts/chartjs.html"><i class="fa fa-circle-o"></i>PPPOE参数</a></li>' +
        '<li><a href="pages/charts/morris.html"><i class="fa fa-circle-o"></i>客户端列表</a></li>' +
        '<li><a href="pages/charts/flot.html"><i class="fa fa-circle-o"></i>无线设置</a></li>' +
        '<li><a href="pages/charts/inline.html"><i class="fa fa-circle-o"></i>DHCP服务器</a></li>' +
        '<li><a href="pages/charts/inline.html"><i class="fa fa-circle-o"></i>设备控制</a></li>' +
        '</ul>' +
        '</li>' +
        '<li class="treeview">' +
        '<a href="#">' +
        '<i class="fa fa-laptop"></i>' +
        '<span>流量分析</span>' +
        '<i class="fa fa-angle-left pull-right"></i>' +
        '</a>' +
        '<ul class="treeview-menu">' +
        '<li><a href="pages/UI/general.html"><i class="fa fa-circle-o"></i> General</a></li>' +
        '<li><a href="pages/UI/icons.html"><i class="fa fa-circle-o"></i> Icons</a></li>' +
        '<li><a href="pages/UI/buttons.html"><i class="fa fa-circle-o"></i> Buttons</a></li>' +
        '<li><a href="pages/UI/sliders.html"><i class="fa fa-circle-o"></i> Sliders</a></li>' +
        '<li><a href="pages/UI/timeline.html"><i class="fa fa-circle-o"></i> Timeline</a></li>' +
        '<li><a href="pages/UI/modals.html"><i class="fa fa-circle-o"></i> Modals</a></li>' +
        '</ul>' +
        '</li>' +

        '<li class="header">系统说明</li>' +
        '<li><a href="#"><i class="fa fa-circle-o text-red"></i> <span>特别注意</span></a></li>' +
        '<li><a href="#"><i class="fa fa-circle-o text-yellow"></i> <span>提醒信息</span></a></li>' +
        '<li><a href="#"><i class="fa fa-circle-o text-aqua"></i> <span>使用帮助</span></a></li>';
    $("#sidebar-menu").html(sidebar);

    var dropdown = '<a href="#" class="dropdown-toggle" data-toggle="dropdown"><img src="/dist/img/user2-160x160.jpg" class="user-image" alt="User Image"><span class="hidden-xs">李正东</span></a>' +
        '<ul class="dropdown-menu">' +
        '<!-- User image -->' +
        '<li class="user-header"><img src="/dist/img/user2-160x160.jpg" class="img-circle" alt="User Image"><p>李正东 - 超级管理员<small>上次登录时间：2015年-10月-19日</small></p></li>' +
        '<!-- Menu Footer-->' +
        '<li class="user-footer"><div class="pull-right"><a href="#" class="btn btn-default btn-flat">退出</a></div></li></ul>';
    $("#dropdown-menu").html(dropdown);
}