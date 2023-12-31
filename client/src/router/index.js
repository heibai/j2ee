import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'
import { title } from '@/settings'

// 同步路由
export const constantRoutes = [
  {
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/redirect/:path(.*)',
        component: () => import('@/views/redirect/index')
      }
    ]
  },
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },
  {
    path: '/auth-redirect',
    component: () => import('@/views/login/auth-redirect'),
    hidden: true
  },
  {
    path: '/404',
    component: () => import('@/views/error-page/404'),
    hidden: true
  },
  {
    path: '/401',
    component: () => import('@/views/error-page/401'),
    hidden: true
  },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [
      {
        path: 'dashboard',
        component: () => import('@/views/dashboard/index'),
        name: 'Dashboard',
        meta: { title: '概览', icon: 'dashboard', affix: true }
      }
    ]
  },
  // 个人信息
  {
    path: '/profile',
    component: Layout,
    redirect: '/profile/index',
    hidden: true,
    children: [
      {
        path: 'index',
        component: () => import('@/views/profile/index'),
        name: 'Profile',
        meta: { title: '个人中心', icon: 'user', noCache: true }
      }
    ]
  }
]

const adminRoutes = [
  //  管理员路由 =========================
  {
    path: '/adminManage',
    component: Layout,
    meta: {
      roles: ['superAdmin']
    },
    children: [
      {
        path: 'index',
        component: () => import('@/views/admin-manage/index'),
        name: 'adminManage',
        meta: {
          title: '管理员管理',
          icon: 'edit'
        }
      }
    ]
  },
  {
    // 公共财产管理
    path: '/publicPropertyManage',
    component: Layout,
    meta: {
      roles: ['superAdmin']
    },
    children: [
      //  TODO 公共财产管理
      {
        path: 'index',
        component: () => import('@/views/property-manage/index'),
        name: 'propertyManage',
        meta: {
          title: '公共财产管理',
          icon: 'star'
        }
      }
    ]
  },

  // {
  //   path: '/buildingManage',
  //   component: Layout,
  //   meta: {
  //     roles: ['superAdmin']
  //   },
  //   children: [
  //     {
  //       path: 'index',
  //       component: () => import('@/views/building-manage/index'),
  //       name: 'cleanRecord',
  //       meta: {
  //         title: '楼栋管理',
  //         icon: 'international'
  //       }
  //     }
  //   ]
  // },
  // {
  //   path: '/floorManage',
  //   component: Layout,
  //   meta: {
  //     roles: ['superAdmin', 'admin']
  //   },
  //   children: [
  //     {
  //       path: 'index',
  //       component: () => import('@/views/floor-manage/index'),
  //       name: 'floorManage',
  //       meta: {
  //         title: '楼层管理',
  //         icon: 'list'
  //       }
  //     }
  //   ]
  // },
  // 物业管理
  {
    path: '/propertyManage',
    component: Layout,
    meta: {
      roles: ['superAdmin', 'admin'],
      title: '物业管理',
      icon: 'international'
    },
    children: [
      // TODO 处理业主投诉或报修意见
      {
        path: 'index',
        component: () => import('@/views/building-manage/index'),
        name: 'cleanRecord',
        meta: {
          title: '物业人员管理'
        }
      },

      {
        path: 'complaintIndex',
        component: () => import('@/views/complaint/index'),
        name: 'complaint-manage',
        meta: {
          title: '投诉管理'
        }
      },
      {
        path: 'repairIndex',
        component: () => import('@/views/repair-report/index'),
        name: 'repair',
        meta: {
          title: '报修管理'
        }
      }
    ]
  },
  // 住户管理
  {
    path: '/residentManage',
    component: Layout,
    meta: {
      roles: ['superAdmin']
    },
    children: [
      // TODO对业主的费用进行管理
      {
        path: 'index',
        component: () => import('@/views/resident-manage/index'),
        name: 'residents',
        meta: {
          title: '住户管理',
          icon: 'people'
        }
      }
    ]
  },
  // 房间管理
  {
    path: '/roomManage',
    component: Layout,
    meta: {
      roles: ['superAdmin']
    },
    children: [
      // TODO对业主的费用进行管理
      {
        path: 'index',
        component: () => import('@/views/room-manage/index'),
        name: 'rooms',
        meta: {
          title: '房间管理',
          icon: 'people'
        }
      }
    ]
  },
  {
    path: '/roomInfo',
    component: Layout,
    meta: {
      roles: ['superAdmin', 'admin']
    },
    children: [
      {
        path: 'index',
        component: () => import('@/views/room-info/index'),
        name: 'roomInfo',
        meta: {
          title: '住房信息',
          icon: 'peoples'
        }
      }
    ]
  },
  {
    path: '/userInfo',
    component: Layout,
    meta: {
      roles: ['superAdmin', 'admin']
    },
    children: [
      {
        path: 'index',
        component: () => import('@/views/user-info/index'),
        name: 'userInfo',
        meta: {
          title: '住户信息',
          icon: 'people'
        }
      }
    ]
  },
  // 费用管理
  {
    path: '/feeManage',
    component: Layout,
    meta: {
      roles: ['superAdmin', 'admin']
    },
    children: [
      {
        path: 'index',
        component: () => import('@/views/fee-manage/index'),
        name: 'feeManage',
        meta: {
          title: '费用管理',
          icon: 'money'
        }
      }
    ]
  }
  // {
  //   path: '/visitorManage',
  //   component: Layout,
  //   meta: {
  //     roles: ['superAdmin']
  //   },
  //   children: [
  //     {
  //       path: 'index',
  //       component: () => import('@/views/visitor-manage/index'),
  //       name: 'visitorManage',
  //       meta: {
  //         title: '住户登记',
  //         icon: 'user'
  //       }
  //     }
  //   ]
  // },
  // 统计模块
  // {
  //   path: '/statistics',
  //   component: Layout,
  //   meta: {
  //     roles: ['superAdmin', 'admin']
  //   },
  //   children: [
  //     // {
  //     //   path: 'index',
  //     //   component: () => import('@/views/statistics/index'),
  //     //   name: 'statistics',
  //     //   meta: {
  //     //     title: '统计模块',
  //     //     icon: 'chart'
  //     //   }
  //     // }
  //   ]
  // }
]

// 业主专属模块
const residentRoutes = [
  // 业主专属页面 =========================
  // TODO基本信息
  // TODO费用情况
  // TODO报修
  // TODO投诉
  {
    path: '/getupRecord',
    component: Layout,
    meta: {
      roles: ['student']
    },
    children: [
      {
        path: 'index',
        component: () => import('@/views/getup-record/index'),
        name: 'getupRecord',
        meta: {
          title: '起床记录',
          icon: 'eye-open'
        }
      }
    ]
  },
  {
    path: '/backRecord',
    component: Layout,
    meta: {
      roles: ['student']
    },
    children: [
      {
        path: 'index',
        component: () => import('@/views/back-record/index'),
        name: 'backRecord',
        meta: {
          title: '归宿记录',
          icon: 'eye'
        }
      }
    ]
  },
  {
    path: '/cleanRecord',
    component: Layout,
    meta: {
      roles: ['student']
    },
    children: [
      {
        path: 'index',
        component: () => import('@/views/clean-record/index'),
        name: 'cleanRecord',
        meta: {
          title: '打扫记录',
          icon: 'theme'
        }
      }
    ]
  }
]

// 异步路由
export const asyncRoutes = [
  // 404 页面要在最后引入
  { path: '*', redirect: '/404', hidden: true },
  //  管理员路由 =========================
  ...adminRoutes,
  //  业主专属模块 =========================
  ...residentRoutes
]

const createRouter = () =>
  new Router({
    // mode: 'history', // require service support
    scrollBehavior: () => ({ y: 0 }),
    routes: [...constantRoutes, ...asyncRoutes]
  })

const router = createRouter()

export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
