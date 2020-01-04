<template>
  <div>
    <!-- 会员退款历史查询项 -->
    <el-main>
      <el-col :span="24">
        <el-form :inline="true" :model="params" class="panel" label-width="100px">
          <el-form-item label="退款渠道">
            <el-select v-model="params.refundChannel" clearable placeholder="请选择渠道">
              <el-option v-for="item in refundList" :key="item.value" :label="item.label" :value="item.value"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="会员卡号">
             <el-input v-model="params.memberNum" placeholder="请输入会员卡号"></el-input>
          </el-form-item>
          <el-form-item label="交易序号">
             <el-input v-model="params.dealSerialNum" placeholder="交易序号"></el-input>
          </el-form-item>
          <el-form-item label="退款日期">
            <el-col :span="11">
              <el-date-picker
                v-model="params.refundBeginTime"
                type="date"
                placeholder="请选择开始日期"
                style="width: 100%;"
                :editable="false"
                :picker-options="beginOptions"
              >
              </el-date-picker>
            </el-col>
            <el-col class="line" :span="1" :offset="1">-</el-col>
            <el-col :span="11">
              <el-date-picker
                v-model="params.refundEndTime"
                type="date"
                placeholder="请选择结束日期"
                style="width: 100%;"
                :editable="false"
                :picker-options="endOptions"
              >
              </el-date-picker>
            </el-col>
          </el-form-item>
          <el-form-item>
            <el-button type="success" @click="searchAction">查询</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-main>
    <!-- 表格数据 -->
    <el-table
      :data="dataList"
      v-loading="loading"
      max-height="600"
      show-summary
      :summary-method="getSummaries"
      element-loading-text="正在拼命加载中">
      <el-table-column label="序号" type="index" width="70"></el-table-column>
      <el-table-column prop="memberNum" label="会员卡号"></el-table-column>
      <el-table-column prop="phoneNum" label="手机号码"></el-table-column>
      <el-table-column prop="memberName" label="姓名"></el-table-column>
      <el-table-column prop="refundChannel" label="退款渠道"></el-table-column>
      <el-table-column prop="refundTime" label="退款日期"></el-table-column>
      <el-table-column prop="refundMoney" label="退款金额(元)">
        <template slot-scope="scope">
          {{ scope.row.refundMoney / 100 }}
        </template>
      </el-table-column>
      <el-table-column prop="dealSerialNum" label="交易序号"></el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button @click="toDetail(scope.row)" type="text">查看详情
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--分页-->
    <el-row class="page" type="flex" justify="center">
      <el-pagination v-show="dataList.length>0" @size-change="handleSizeChange" @current-change="handleCurrentChange"
                     :page-sizes="[10, 20, 30, 40]" :page-size.sync="params.page.pageSize" background layout="total, sizes, prev, pager, next, jumper"
                     :total="params.total" :current-page.sync="params.page.currentPage"></el-pagination>
    </el-row>
  </div>
</template>
<script>
// import breadcrumb from '@/components/breadcrumb'
// import api from '@/api/statistics-search'
export default {
  data () {
    return {
      breadcrumb: [
        {
          name: '统计查询分析'
        },
        {
          name: '会员退款历史'
        }
      ],
      loading: false,
      params: {
        refundChannel: 'P',
        channelValue: '',
        refundMoneyBegin: '',
        refundMoneyEnd: '',
        refundBeginTime: '',
        refundEndTime: 'P',
        dealSerialNum: '',
        memberNum: '',
        page: {
          currentPage: 1,
          pageSize: 10,
          totalSizeFlag: 'T'
        }
      },
      beginOptions: {
        disabledDate: time => {
          return time.getTime() > this.params.consumeEndTime
        }
      },
      endOptions: {
        disabledDate: time => {
          return time.getTime() < this.params.consumeBeginTime
        }
      },
      refundList: [
        { label: '全部', value: '' },
        { label: '电商', value: 'A' },
        { label: '微信', value: 'W' },
        { label: 'POS系统', value: 'P' },
        { label: '一体化会员', value: 'Y' },
        { label: '一体化采配销', value: 'E' },
        { label: '短信平台', value: 'F' },
        { label: '管理平台', value: 'M' },
        { label: '三方平台', value: 'O' }
      ],
      dataList: []
    }
  },
  methods: {
    handleSizeChange (val) {
      this.params.page.currentPage = 1
      this.searchAction()
    },
    handleCurrentChange (val) {
      this.searchAction()
    },
    // 查询
    searchAction () {
      this.loading = true
      // api
      //   .getRefundList(Object.assign({}, this.params))
      //   .then(res => {
      //     this.dataList = res.consumeRefundList
      //     this.params.total = res.totalSize
      //     this.$store.commit('changeSearchInfo', {
      //       key: 'memberRefund',
      //       value: Object.assign({}, this.params)
      //     })
      //     this.loading = false
      //   })
      //   .catch(err => {
      //     if (err) {
      //       this.loading = false
      //     }
      //   })
    },
    // 查看详情
    toDetail (row) {
      this.$router.push(`/memberRefundDetail?refundId=${row.refundId}`)
    },
    getSummaries (param) {
      const { columns, data } = param
      const sums = []
      columns.forEach((column, index) => {
        if (index === 0) {
          sums[index] = '合计'
          return
        }
        // 退款金额列
        if (index === 6) {
          const values = data.map(item => Number(item[column.property]))
          sums[index] = values.reduce((prev, curr) => {
            const value = Number(curr)
            if (!isNaN(value)) {
              return prev + curr
            } else {
              return prev
            }
          }, 0)
          sums[index] = sums[index] / 100
        }
      })
      return sums
    }
  },
  mounted () {
    let searchObj = this.$store.state.searchInfo.searchInfo.memberRefund
    if (searchObj) {
      this.params = Object.assign({}, searchObj)
      delete this.params.total
    }
  }
}
</script>
