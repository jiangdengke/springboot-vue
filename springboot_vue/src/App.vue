<template>
  <div>
<!--  头部  -->
    <Header/>
<!-- 主体 -->
    <div style="display: flex" ><!--style="display: flex"可将主体分为侧边栏和内容两部分-->
      <!--侧边栏-->
      <Aside/>
      <!--内容-->
      <!--通过路由机制-->
      <router-view style="flex: 1"/>
    </div>
  </div>
</template>

<style>

</style>
<script>
/**使用组件先引入再导出
 * */
import Header from "@/components/Header.vue";
import Aside from "@/components/Aside.vue"
export default {
  name:"Layout",
  components:{
    Header,
    Aside
  }
}
</script>