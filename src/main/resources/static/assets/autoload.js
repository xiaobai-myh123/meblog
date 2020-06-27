try {
    $.ajax({
        url: 'assets/waifu-tips.min.js',
        dataType: "script",
        cache: true,
        async: false
    });
    $.ajax({
        url: 'assets/live2d.min.js',
        dataType: "script",
        cache: true,
        async: false
    });
    /* 可直接修改部分参数 */
    live2d_settings['hitokotoAPI'] = 'hitokoto.cn'; // 一言 API
    live2d_settings['modelId'] = 6; // 默认模型 ID
    live2d_settings['modelTexturesId'] = 18; // 默认材质 ID
    live2d_settings['modelStorage'] = false; // 不储存模型 ID
    live2d_settings['waifuDraggable'] = 'axis-x';    // 拖拽样式
    /* 在 initModel 前添加 */
    initModel('assets/waifu-tips.json');
} catch (err) {
    console.log('[Error] JQuery is not defined.')
}