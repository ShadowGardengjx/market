<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="common/head.jsp"%>
<div class="right_col" role="main">
    <div class="dashboard-container">
        <!-- 页面标题 -->
        <div class="page-header">
            <h1>销售数据分析看板</h1>
            <p class="subtitle">实时监控产品销售情况和趋势</p>
        </div>

        <!-- 主要数据概览 -->
        <div class="stats-overview">
            <div class="stat-card">
                <div class="stat-icon" style="background: #ff6b6b;">
                    <i class="fa fa-shopping-cart"></i>
                </div>
                <div class="stat-info">
                    <h3>140</h3>
                    <p>总销量(件)</p>
                </div>
            </div>
            <div class="stat-card">
                <div class="stat-icon" style="background: #4ecdc4;">
                    <i class="fa fa-yuan"></i>
                </div>
                <div class="stat-info">
                    <h3>470.0</h3>
                    <p>总销售额(元)</p>
                </div>
            </div>
            <div class="stat-card">
                <div class="stat-icon" style="background: #45b7d1;">
                    <i class="fa fa-line-chart"></i>
                </div>
                <div class="stat-info">
                    <h3>3</h3>
                    <p>在售商品</p>
                </div>
            </div>
            <div class="stat-card">
                <div class="stat-icon" style="background: #96ceb4;">
                    <i class="fa fa-trophy"></i>
                </div>
                <div class="stat-info">
                    <h3>冰红茶</h3>
                    <p>热销品类</p>
                </div>
            </div>
        </div>

        <!-- 图表区域 -->
        <div class="charts-grid">
            <!-- 左侧：详细分析图表 -->
            <div class="analysis-section">
                <!-- 销量分布 -->
                <div class="chart-wrapper">
                    <div class="chart-header">
                        <h3>销量比例统计</h3>
                        <span class="chart-subtitle">各产品销售占比分析</span>
                    </div>
                    <div id="mainChart" class="chart-content"></div>
                </div>

                <!-- 销售趋势 -->
                <div class="chart-wrapper">
                    <div class="chart-header">
                        <h3>销售趋势分析</h3>
                        <span class="chart-subtitle">月度销售数据趋势</span>
                    </div>
                    <div id="trendChart" class="chart-content"></div>
                </div>

                <!-- 销售金额 -->
                <div class="chart-wrapper">
                    <div class="chart-header">
                        <h3>销售金额统计</h3>
                        <span class="chart-subtitle">各产品销售额对比</span>
                    </div>
                    <div id="amountChart" class="chart-content"></div>
                </div>
            </div>

            <!-- 右侧：热销榜单和快速洞察 -->
            <div class="insight-section">
                <!-- 热销产品榜单 -->
                <div class="chart-wrapper highlight">
                    <div class="chart-header">
                        <h3>🏆 热销产品榜单</h3>
                        <span class="chart-subtitle">销售额排名TOP3</span>
                    </div>
                    <div id="hotProductsChart" class="chart-content"></div>
                </div>

                <!-- 数据洞察 -->
                <div class="insight-card">
                    <h4>📊 数据洞察</h4>
                    <div class="insight-list">
                        <div class="insight-item">
                            <span class="insight-label">最佳表现产品</span>
                            <span class="insight-value">冰红茶（原味）</span>
                        </div>
                        <div class="insight-item">
                            <span class="insight-label">最高销售额</span>
                            <span class="insight-value">¥200.00</span>
                        </div>
                        <div class="insight-item">
                            <span class="insight-label">数据更新时间</span>
                            <span class="insight-value" id="updateTime"></span>
                        </div>
                    </div>
                </div>

                <!-- 快速操作 -->
                <div class="action-card">
                    <h4>⚡ 快速操作</h4>
                    <div class="action-buttons">
                        <button class="btn-action" onclick="exportData()">
                            <i class="fa fa-download"></i>
                            导出数据
                        </button>
                        <button class="btn-action" onclick="refreshData()">
                            <i class="fa fa-refresh"></i>
                            刷新数据
                        </button>
                        <button class="btn-action" onclick="showDetails()">
                            <i class="fa fa-bar-chart"></i>
                            详细报告
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="common/footer.jsp"%>

<style>
/* 基础样式重置 */
.dashboard-container {
    max-width: 1400px;
    margin: 0 auto;
    padding: 20px;
}

/* 页面标题 */
.page-header {
    text-align: center;
    margin-bottom: 30px;
    padding: 20px 0;
    border-bottom: 1px solid #eaeaea;
}

.page-header h1 {
    color: #2c3e50;
    margin: 0 0 10px 0;
    font-size: 2.2em;
    font-weight: 300;
}

.subtitle {
    color: #7f8c8d;
    font-size: 1.1em;
    margin: 0;
}

/* 数据概览卡片 */
.stats-overview {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(240px, 1fr));
    gap: 20px;
    margin-bottom: 30px;
}

.stat-card {
    background: white;
    border-radius: 12px;
    padding: 25px;
    display: flex;
    align-items: center;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
    border: 1px solid #eaeaea;
    transition: transform 0.2s ease, box-shadow 0.2s ease;
}

.stat-card:hover {
    transform: translateY(-2px);
    box-shadow: 0 6px 20px rgba(0, 0, 0, 0.12);
}

.stat-icon {
    width: 60px;
    height: 60px;
    border-radius: 12px;
    display: flex;
    align-items: center;
    justify-content: center;
    margin-right: 20px;
    color: white;
    font-size: 24px;
}

.stat-info h3 {
    margin: 0 0 5px 0;
    font-size: 1.8em;
    color: #2c3e50;
    font-weight: 600;
}

.stat-info p {
    margin: 0;
    color: #7f8c8d;
    font-size: 0.9em;
}

/* 图表网格布局 */
.charts-grid {
    display: grid;
    grid-template-columns: 2fr 1fr;
    gap: 25px;
    align-items: start;
}

.analysis-section {
    display: flex;
    flex-direction: column;
    gap: 25px;
}

.insight-section {
    display: flex;
    flex-direction: column;
    gap: 25px;
}

/* 图表包装器 */
.chart-wrapper {
    background: white;
    border-radius: 12px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
    border: 1px solid #eaeaea;
    overflow: hidden;
}

.chart-wrapper.highlight {
    border-left: 4px solid #e74c3c;
    box-shadow: 0 4px 16px rgba(231, 76, 60, 0.15);
}

.chart-header {
    padding: 20px 25px 15px;
    border-bottom: 1px solid #f8f9fa;
    background: linear-gradient(135deg, #f8f9fa 0%, #ffffff 100%);
}

.chart-header h3 {
    margin: 0 0 5px 0;
    color: #2c3e50;
    font-size: 1.3em;
    font-weight: 600;
}

.chart-subtitle {
    color: #7f8c8d;
    font-size: 0.9em;
}

.chart-content {
    width: 100%;
    height: 320px;
    padding: 10px;
}

/* 洞察卡片 */
.insight-card, .action-card {
    background: white;
    border-radius: 12px;
    padding: 25px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
    border: 1px solid #eaeaea;
}

.insight-card h4, .action-card h4 {
    margin: 0 0 20px 0;
    color: #2c3e50;
    font-size: 1.1em;
    font-weight: 600;
}

.insight-list {
    display: flex;
    flex-direction: column;
    gap: 15px;
}

.insight-item {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 12px 0;
    border-bottom: 1px solid #f8f9fa;
}

.insight-item:last-child {
    border-bottom: none;
}

.insight-label {
    color: #7f8c8d;
    font-size: 0.9em;
}

.insight-value {
    color: #2c3e50;
    font-weight: 600;
    font-size: 0.95em;
}

/* 操作按钮 */
.action-buttons {
    display: flex;
    flex-direction: column;
    gap: 12px;
}

.btn-action {
    display: flex;
    align-items: center;
    gap: 10px;
    padding: 12px 20px;
    border: 1px solid #e0e0e0;
    border-radius: 8px;
    background: white;
    color: #555;
    cursor: pointer;
    transition: all 0.2s ease;
    font-size: 0.9em;
}

.btn-action:hover {
    background: #f8f9fa;
    border-color: #007bff;
    color: #007bff;
    transform: translateX(5px);
}

/* 响应式设计 */
@media (max-width: 1200px) {
    .charts-grid {
        grid-template-columns: 1fr;
    }

    .stats-overview {
        grid-template-columns: repeat(2, 1fr);
    }
}

@media (max-width: 768px) {
    .dashboard-container {
        padding: 15px;
    }

    .stats-overview {
        grid-template-columns: 1fr;
    }

    .chart-content {
        height: 280px;
    }

    .page-header h1 {
        font-size: 1.8em;
    }
}

@media (max-width: 480px) {
    .stat-card {
        flex-direction: column;
        text-align: center;
    }

    .stat-icon {
        margin-right: 0;
        margin-bottom: 15px;
    }

    .action-buttons {
        flex-direction: column;
    }
}
</style>

<script type="text/javascript">
    // 设置更新时间
    document.getElementById('updateTime').textContent = new Date().toLocaleString();

    // 加载饼图数据
    $.ajax({
        method:'post',
        url:'${pageContext.request.contextPath}/manage/flatform/sale/echartsData',
        dataType:'json',
        success:function(data){
            console.log('饼图数据:', data);
            initPieChart(data);
        },
        error:function(xhr, status, error){
            console.error('加载饼图数据失败:', error);
            showChartError('mainChart', '饼图数据加载失败');
        }
    });

    // 加载趋势图数据
    $.ajax({
        method:'post',
        url:'${pageContext.request.contextPath}/manage/flatform/sale/trendData',
        dataType:'json',
        success:function(data){
            console.log('趋势图数据:', data);
            initTrendChart(data);
        },
        error:function(xhr, status, error){
            console.error('加载趋势图数据失败:', error);
            showChartError('trendChart', '趋势图数据加载失败');
        }
    });

    // 加载销售金额数据
    $.ajax({
        method:'post',
        url:'${pageContext.request.contextPath}/manage/flatform/sale/amountData',
        dataType:'json',
        success:function(data){
            console.log('销售金额数据:', data);
            initAmountChart(data);
        },
        error:function(xhr, status, error){
            console.error('加载销售金额数据失败:', error);
            showChartError('amountChart', '销售金额数据加载失败');
        }
    });

    // 图表错误处理
    function showChartError(chartId, message) {
        var chartDom = document.getElementById(chartId);
        chartDom.innerHTML = `
            <div style="display: flex; flex-direction: column; align-items: center; justify-content: center; height: 100%; color: #7f8c8d;">
                <i class="fa fa-exclamation-triangle" style="font-size: 48px; margin-bottom: 16px;"></i>
                <p>${message}</p>
                <button onclick="location.reload()" style="margin-top: 12px; padding: 8px 16px; background: #3498db; color: white; border: none; border-radius: 4px; cursor: pointer;">
                    重新加载
                </button>
            </div>
        `;
    }

    // 初始化饼图
    function initPieChart(data){
        var myChart = echarts.init(document.getElementById('mainChart'));
        var option = {
            title: {
                text: '销量比例',
                left: 'center',
                textStyle: {
                    fontSize: 14,
                    color: '#7f8c8d'
                }
            },
            tooltip: {
                trigger: 'item',
                formatter: '{a} <br/>{b}: {c} ({d}%)'
            },
            legend: {
                orient: 'horizontal',
                bottom: 10,
                data: data.map(item => item.name),
                textStyle: {
                    fontSize: 12
                }
            },
            series: [{
                name: '销量',
                type: 'pie',
                radius: ['45%', '70%'],
                center: ['50%', '45%'],
                data: data,
                itemStyle: {
                    borderRadius: 6,
                    borderColor: '#fff',
                    borderWidth: 2
                },
                emphasis: {
                    itemStyle: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                },
                label: {
                    formatter: '{b}\n{d}%',
                    fontSize: 12
                }
            }]
        };
        myChart.setOption(option);

        window.addEventListener('resize', function() {
            myChart.resize();
        });
    };

    // 初始化趋势图
    function initTrendChart(data){
        var trendChart = echarts.init(document.getElementById('trendChart'));
        var option = {
            title: {
                text: '销售趋势',
                left: 'center',
                textStyle: {
                    fontSize: 14,
                    color: '#7f8c8d'
                }
            },
            tooltip: {
                trigger: 'axis',
                formatter: function(params) {
                    let result = params[0].name + '<br/>';
                    params.forEach(function(item) {
                        result += item.marker + item.seriesName + ': ' + item.value + '件<br/>';
                    });
                    return result;
                }
            },
            legend: {
                data: data.productNames || [],
                bottom: 10,
                type: 'scroll',
                textStyle: {
                    fontSize: 11
                }
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '15%',
                top: '15%',
                containLabel: true
            },
            xAxis: {
                type: 'category',
                boundaryGap: false,
                data: data.months || [],
                axisLabel: {
                    fontSize: 11
                }
            },
            yAxis: {
                type: 'value',
                name: '销售量(件)',
                axisLabel: {
                    fontSize: 11
                }
            },
            series: (data.seriesData || []).map(series => {
                return {
                    ...series,
                    smooth: true,
                    lineStyle: {
                        width: 3
                    },
                    symbolSize: 6
                };
            })
        };

        trendChart.setOption(option);

        window.addEventListener('resize', function() {
            trendChart.resize();
        });
    };

    // 初始化销售金额柱状图
    function initAmountChart(data){
        var amountChart = echarts.init(document.getElementById('amountChart'));
        var option = {
            title: {
                text: '销售金额',
                left: 'center',
                textStyle: {
                    fontSize: 14,
                    color: '#7f8c8d'
                }
            },
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    type: 'shadow'
                },
                formatter: function(params) {
                    let result = params[0].name + '<br/>';
                    params.forEach(function(item) {
                        var amount = Number(item.value);
                        var formattedAmount = amount >= 10000 ?
                            (amount / 10000).toFixed(2) + '万元' :
                            amount.toFixed(2) + '元';
                        result += item.marker + item.seriesName + ': ' + formattedAmount + '<br/>';
                    });
                    return result;
                }
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '15%',
                top: '15%',
                containLabel: true
            },
            xAxis: {
                type: 'category',
                data: data.productNames || [],
                axisLabel: {
                    interval: 0,
                    rotate: 45,
                    fontSize: 11
                }
            },
            yAxis: {
                type: 'value',
                name: '金额(元)',
                axisLabel: {
                    formatter: function(value) {
                        if (value >= 10000) {
                            return (value / 10000).toFixed(1) + '万';
                        }
                        return value;
                    },
                    fontSize: 11
                }
            },
            series: [{
                name: '销售金额',
                type: 'bar',
                data: data.amounts || [],
                itemStyle: {
                    color: function(params) {
                        var colorList = ['#5470c6', '#91cc75', '#fac858', '#ee6666', '#73c0de'];
                        return colorList[params.dataIndex % colorList.length];
                    }
                },
                label: {
                    show: true,
                    position: 'top',
                    formatter: function(params) {
                        var value = params.value;
                        if (value >= 10000) {
                            return (value / 10000).toFixed(1) + '万';
                        }
                        return value;
                    },
                    fontSize: 11
                }
            }]
        };

        amountChart.setOption(option);

        window.addEventListener('resize', function() {
            amountChart.resize();
        });
    };

    // 操作函数
    function exportData() {
        // 1. 选中要导出的主要内容区域
        const exportArea = document.querySelector('.dashboard-container');

        // 2. 临时显示导出提示
        const btn = event.target;
        btn.disabled = true;
        btn.innerHTML = '<i class="fa fa-spinner fa-spin"></i> 导出中...';

        // 3. 使用 html2canvas 渲染
        html2canvas(exportArea, {
            scale: 2, // 提高清晰度
            useCORS: true,
            logging: false,
            backgroundColor: '#ffffff'
        }).then(canvas => {
            const imgData = canvas.toDataURL('image/png');

            // 4. 创建 PDF
            const { jsPDF } = window.jspdf;
            const pdf = new jsPDF('p', 'mm', 'a4');

            const pdfWidth = pdf.internal.pageSize.getWidth();
            const pdfHeight = pdf.internal.pageSize.getHeight();
            const imgWidth = pdfWidth;
            const imgHeight = canvas.height * pdfWidth / canvas.width;

            let position = 0;

            if (imgHeight < pdfHeight) {
                pdf.addImage(imgData, 'PNG', 0, 0, imgWidth, imgHeight);
            } else {
                let heightLeft = imgHeight;
                let y = 0;
                while (heightLeft > 0) {
                    pdf.addImage(imgData, 'PNG', 0, y, imgWidth, imgHeight);
                    heightLeft -= pdfHeight;
                    if (heightLeft > 0) pdf.addPage();
                    y -= pdfHeight;
                }
            }

            // 5. 下载 PDF
            const fileName = '销售数据分析看板_' + new Date().toLocaleDateString() + '.pdf';
            pdf.save(fileName);

            // 6. 恢复按钮状态
            btn.disabled = false;
            btn.innerHTML = '<i class="fa fa-download"></i> 导出数据';

            alert('✅ 已成功导出为 PDF 文件，请查看浏览器下载目录或桌面。');
        }).catch(err => {
            console.error('导出失败:', err);
            alert('❌ 导出失败，请重试');
            btn.disabled = false;
            btn.innerHTML = '<i class="fa fa-download"></i> 导出数据';
        });
    }


    function refreshData() {
        location.reload();
    }

    function showDetails() {
        // 创建模态框显示详细报告
        const modalHtml = `
            <div class="modal fade" id="detailReportModal" tabindex="-1" role="dialog">
                <div class="modal-dialog modal-xl" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">销售详细报告</h5>
                            <button type="button" class="close" data-dismiss="modal">
                                <span>&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <div id="reportContent">
                                <div class="text-center">
                                    <i class="fa fa-spinner fa-spin"></i> 生成报告中...
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>
                            <button type="button" class="btn btn-primary" onclick="exportReport()">导出Excel</button>
                        </div>
                    </div>
                </div>
            </div>
        `;

        // 添加模态框到页面
        if (!$('#detailReportModal').length) {
            $('body').append(modalHtml);
        }

        $('#detailReportModal').modal('show');

        // 加载报告数据
        loadQuickReport();
    }

    function loadQuickReport() {
        $.ajax({
            method: 'POST',
            url: '${pageContext.request.contextPath}/manage/flatform/sale/detailedReportData',
            dataType: 'json',
            success: function(data) {
                renderQuickReport(data);
            },
            error: function() {
                $('#reportContent').html('<div class="alert alert-danger">加载报告失败</div>');
            }
        });
    }

    function renderQuickReport(data) {
        const html = `
            <div class="quick-report">
                <div class="row mb-3">
                    <div class="col-md-3">
                        <div class="card text-white bg-primary">
                            <div class="card-body">
                                <h4>¥\${(data.summary?.totalAmount || 0).toLocaleString()}</h4>
                                <p>总销售额</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="card text-white bg-success">
                            <div class="card-body">
                                <h4>\${(data.summary?.totalVolume || 0).toLocaleString()}</h4>
                                <p>总销售量</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="card text-white bg-warning">
                            <div class="card-body">
                                <h4>\${data.productDetails?.length || 0}</h4>
                                <p>活跃产品</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="card text-white bg-info">
                            <div class="card-body">
                                <h4>\${data.customerAnalysis?.length || 0}</h4>
                                <p>活跃客户</p>
                            </div>
                        </div>
                    </div>
                </div>

                <h5>热销产品TOP3</h5>
                <ul class="list-group">
                    \${(data.productDetails || []).slice(0, 3).map(product =>
                        `<li class="list-group-item d-flex justify-content-between align-items-center">
                            \${product.productName}
                            <span class="badge badge-primary badge-pill">
                                ¥\${product.totalAmount.toLocaleString()}
                            </span>
                        </li>`
                    ).join('')}
                </ul>
            </div>
        `;
        $('#reportContent').html(html);
    }
</script>

<script type="text/javascript">
    function loadHotProducts() {
        $.ajax({
            url: "${pageContext.request.contextPath}/manage/flatform/sale/hotProducts",
            type: "get",
            dataType: "json",
            success: function (data) {
                var chartDom = document.getElementById('hotProductsChart');
                var myChart = echarts.init(chartDom);
                var option = {
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            type: 'shadow'
                        },
                        formatter: function(params) {
                            var value = params[0].value;
                            var formattedValue = value >= 10000 ?
                                (value / 10000).toFixed(2) + '万元' :
                                value.toFixed(2) + '元';
                            return params[0].name + '<br/>销售额: ' + formattedValue;
                        }
                    },
                    grid: {
                        left: '3%',
                        right: '4%',
                        bottom: '3%',
                        top: '10%',
                        containLabel: true
                    },
                    xAxis: {
                        type: 'value',
                        axisLabel: {
                            formatter: function(value) {
                                if (value >= 10000) {
                                    return (value / 10000).toFixed(1) + '万';
                                }
                                return value;
                            },
                            fontSize: 11
                        }
                    },
                    yAxis: {
                        type: 'category',
                        data: data.productNames,
                        axisLabel: {
                            fontSize: 12,
                            fontWeight: 'bold'
                        }
                    },
                    series: [{
                        name: '销售额',
                        type: 'bar',
                        data: data.amounts,
                        itemStyle: {
                            color: function(params) {
                                // 渐变色，第一名更突出
                                var colors = ['#c23531', '#2f4554', '#61a0a8'];
                                return colors[params.dataIndex] || '#91c7ae';
                            }
                        },
                        label: {
                            show: true,
                            position: 'right',
                            formatter: function(params) {
                                var value = params.value;
                                if (value >= 10000) {
                                    return (value / 10000).toFixed(1) + '万';
                                }
                                return value;
                            },
                            fontSize: 11,
                            fontWeight: 'bold'
                        }
                    }]
                };
                myChart.setOption(option);

                window.addEventListener('resize', function() {
                    myChart.resize();
                });
            },
            error: function () {
                showChartError('hotProductsChart', '热销榜单数据加载失败');
            }
        });
    }

    // 页面加载时自动加载
    $(document).ready(function () {
        loadHotProducts();
    });
</script>

<!-- html2canvas：网页转图片 -->
<script src="https://cdn.jsdelivr.net/npm/html2canvas@1.4.1/dist/html2canvas.min.js"></script>
<!-- jsPDF：生成 PDF -->
<script src="https://cdn.jsdelivr.net/npm/jspdf@2.5.1/dist/jspdf.umd.min.js"></script>

<script src="${pageContext.request.contextPath }/statics/js/echarts.min.js"></script>