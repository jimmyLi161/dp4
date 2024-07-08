<!--
 * @FileDescription: AppBar
 -->
<template>
  <v-app-bar
    id="app-bar"
    absolute
    app
    color="transparent"
    flat
    height="75"
  >
    <v-btn
      class="mr-3"
      elevation="1"
      fab
      small
      @click="setDrawer(!drawer)"
    >
      <v-icon v-if="value">
        mdi-view-quilt
      </v-icon>

      <v-icon v-else>
        mdi-dots-vertical
      </v-icon>
    </v-btn>

    <v-spacer />

    <div class="mx-3" />

    <v-menu
      bottom
      left
      offset-y
      origin="top right"
      transition="scale-transition"
    >
      <template v-slot:activator="{ attrs, on }">
        <v-btn
          class="ml-2"
          min-width="0"
          text
          v-bind="attrs"
          v-on="on"
        >
            <v-icon>mdi-account</v-icon>
        </v-btn>
      </template>

      <v-list
        :tile="false"
        nav
      >
        <div>
          <app-bar-item
            v-for="(n, i) in functions"
            :key="`item-${i}`"
          >
            <v-list-item-title v-text="n" @click="logout" />
          </app-bar-item>
        </div>
      </v-list>
    </v-menu>
  </v-app-bar>
</template>

<script>
  // Components
  import { VHover, VListItem } from 'vuetify/lib'

  // Utilities
  import { mapState, mapMutations } from 'vuex'

  export default {
    name: 'DashboardCoreAppBar',

    components: {
      AppBarItem: {
        render (h) {
          return h(VHover, {
            scopedSlots: {
              default: ({ hover }) => {
                return h(VListItem, {
                  attrs: this.$attrs,
                  class: {
                    'black--text': !hover,
                    'white--text secondary elevation-12': hover,
                  },
                  props: {
                    activeClass: '',
                    dark: hover,
                    link: true,
                    ...this.$attrs,
                  },
                }, this.$slots.default)
              },
            },
          })
        },
      },
    },

    props: {
      value: {
        type: Boolean,
        default: false,
      },
    },

    data: () => ({
      functions: [
        'Log out',
      ],
      page_name: '首页'
    }),

    computed: {
      ...mapState(['drawer']),
    },

    methods: {
      ...mapMutations({
        setDrawer: 'SET_DRAWER',
      }),
      logout() {
        let that = this;
        fetch('http://localhost:9095/logout', {
          method: 'POST',
          headers: new Headers({
            'Content-Type': 'application/json'
          }),
          body: JSON.stringify({
            username: that.$store.state.username,
            password: that.$store.state.password,
          })
        }).then(res => {
          if (res.ok) {
            that.$store.commit("SET_USER_NAME", undefined);
            that.$store.commit("SET_PASSWORD", undefined);
            that.$store.commit("SET_TOKEN", undefined);
            that.$store.commit("SET_USER_NAME", undefined);
            that.$store.commit("SET_EMP_NAME", undefined);
            that.$store.commit("SET_DEPT", undefined);
            that.$store.commit("SET_DEPT_ID", undefined);
            that.$store.commit("SET_AUTH", undefined);
            that.$store.commit("SET_GENDER", undefined);
            that.$store.commit("SET_BIRTHDAY", undefined);
            that.$store.commit("SET_ENTRY_DAY", undefined);
            that.$router.push('/login');
          }
        })
      }
    },
  }
</script>
