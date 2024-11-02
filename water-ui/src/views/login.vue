<template>
  <div class="login-bg">
    <div class="login-logo">
      <h1>大荔绿泉小程序后台管理系统</h1>
    </div>
    <div class="login-main">
<!--      <h5 class="login-title">欢迎登录</h5>-->
      <div class="login-content">
        <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form">
          <h3 class="title">欢迎登录</h3>
          <el-form-item prop="username">
            <el-input
              v-model="loginForm.username"
              type="text"
              auto-complete="off"
              placeholder="账号"
            >
              <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon" />
            </el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input
              v-model="loginForm.password"
              type="password"
              auto-complete="off"
              placeholder="密码"
              @keyup.enter.native="handleLogin"
            >
              <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon" />
            </el-input>
          </el-form-item>
          <el-form-item prop="code" v-if="captchaEnabled">
            <el-input
              v-model="loginForm.code"
              auto-complete="off"
              placeholder="验证码"
              style="width: 63%"
              @keyup.enter.native="handleLogin"
            >
              <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon" />
            </el-input>
            <div class="login-code">
              <img :src="codeUrl" @click="getCode" class="login-code-img"/>
            </div>
          </el-form-item>
          <el-checkbox v-model="loginForm.rememberMe" style="margin:0px 0px 25px 0px;">记住密码</el-checkbox>
          <el-form-item style="width:100%;">
            <el-button
              :loading="loading"
              size="medium"
              type="primary"
              style="width:100%;"
              @click.native.prevent="handleLogin"
            >
              <span v-if="!loading">登 录</span>
              <span v-else>登 录 中...</span>
            </el-button>
            <div style="float: right;" v-if="register">
              <router-link class="link-type" :to="'/register'">立即注册</router-link>
            </div>
          </el-form-item>
        </el-form>
      </div>
    </div>
    <!--  底部  -->
    <div class="el-login-footer">
      <span>Copyright © 2024 大荔绿泉小程序.</span>
    </div>
  </div>
</template>

<script>
import { getCodeImg } from "@/api/login";
import Cookies from "js-cookie";
import { encrypt, decrypt } from '@/utils/jsencrypt'

export default {
  name: "Login",
  data() {
    return {
      codeUrl: "",
      loginForm: {
        username: "admin",
        password: "admin123",
        rememberMe: false,
        code: "",
        uuid: ""
      },
      loginRules: {
        username: [
          { required: true, trigger: "blur", message: "请输入您的账号" }
        ],
        password: [
          { required: true, trigger: "blur", message: "请输入您的密码" }
        ],
        code: [{ required: true, trigger: "change", message: "请输入验证码" }]
      },
      loading: false,
      // 验证码开关
      captchaEnabled: true,
      // 注册开关
      register: false,
      redirect: undefined
    };
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect;
      },
      immediate: true
    }
  },
  created() {
    this.getCode();
    this.getCookie();
  },
  methods: {
    getCode() {
      getCodeImg().then(res => {
        this.captchaEnabled = res.captchaEnabled === undefined ? true : res.captchaEnabled;
        if (this.captchaEnabled) {
          this.codeUrl = "data:image/gif;base64," + res.img;
          this.loginForm.uuid = res.uuid;
        }
      });
    },
    getCookie() {
      const username = Cookies.get("username");
      const password = Cookies.get("password");
      const rememberMe = Cookies.get('rememberMe')
      this.loginForm = {
        username: username === undefined ? this.loginForm.username : username,
        password: password === undefined ? this.loginForm.password : decrypt(password),
        rememberMe: rememberMe === undefined ? false : Boolean(rememberMe)
      };
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true;
          if (this.loginForm.rememberMe) {
            Cookies.set("username", this.loginForm.username, { expires: 30 });
            Cookies.set("password", encrypt(this.loginForm.password), { expires: 30 });
            Cookies.set('rememberMe', this.loginForm.rememberMe, { expires: 30 });
          } else {
            Cookies.remove("username");
            Cookies.remove("password");
            Cookies.remove('rememberMe');
          }
          this.$store.dispatch("Login", this.loginForm).then(() => {
            this.$router.push({ path: this.redirect || "/" }).catch(()=>{});
          }).catch(() => {
            this.loading = false;
            if (this.captchaEnabled) {
              this.getCode();
            }
          });
        }
      });
    }
  }
};
</script>

<style rel="stylesheet/scss" lang="scss">
.login {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  background-image: url("../assets/images/background.png");
  background-size: cover;
}
.title {
  margin: 0px auto 30px auto;
  text-align: center;
  color: #707070;
}

.login-form {
  border-radius: 6px;
  background: #ffffff;
  width: 400px;
  padding: 25px 25px 5px 25px;
  .el-input {
    height: 38px;
    input {
      height: 38px;
    }
  }
  .input-icon {
    height: 39px;
    width: 14px;
    margin-left: 2px;
  }
}
.login-tip {
  font-size: 13px;
  text-align: center;
  color: #bfbfbf;
}
.login-code {
  width: 33%;
  height: 38px;
  float: right;
  img {
    cursor: pointer;
    vertical-align: middle;
  }
}
.el-login-footer {
  height: 40px;
  line-height: 40px;
  position: fixed;
  bottom: 0;
  width: 100%;
  text-align: center;
  color: #fff;
  font-family: Arial;
  font-size: 12px;
  letter-spacing: 1px;
}
.login-code-img {
  height: 38px;
}

* {
  box-sizing: border-box;
  margin: 0;
  padding: 0;
  border: 0;
  list-style: none;
  outline: none;
}
a, a:hover {
  text-decoration: none;
}
input::-webkit-input-placeholder {
  color: #A5A2C4;
}
input::-moz-placeholder {  /* Mozilla Firefox 19+ */
  color: #A5A2C4;
}
input:-moz-placeholder {  /* Mozilla Firefox 4 to 18 */
  color: #A5A2C4;
}
input:-ms-input-placeholder {  /* Internet Explorer 10-11 */
  color: #A5A2C4;
}

html {
  width: 100%;
  height: 100%;
}
.login-bg {
  width: 100%;
  height: 100%;
  background: url(../assets/images/background.png) no-repeat center center;
  background-size: cover;
  padding: 40px 40px;
}
.login-logo {
  padding: 20px 20px;
}
.login-logo h1 {
  display: block;
  font-size: 36px;
  color: #000;
  font-weight: bold;
  line-height: 1.5;
}
.login-main {
  position: fixed;
  right: 15%;
  top: 50%;
  transform: translateY(-50%);
}
.login-title {
  font-size: 32px;
  color: #101828;
  text-align: center;
  padding: 20px 0;
}
.login-content {
  width: 500px;
  background: linear-gradient(180deg, #fff, #ecf2fd);
  background: #fff;
  border-radius: 10px;
  padding: 50px;
}
.login-group {
  display: flex;
  flex-direction: column;
  margin-bottom: 20px;
}
.login-group label {
  font-size: 16px;
  color: #101828;
  margin-bottom: 10px;
  line-height: 1.5;
}
.login-group input {
  display: block;
  border-radius: 5px;
  border: 1px solid #eaecf0;
  font-size: 16px;
  height: 50px;
  line-height: 50px;
  margin: 0;
  padding: 0 20px;
}
.login-group input:hover {
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.08);
}
.login-group input:focus {
  border-color: #155eef;
}
.login-btn {
  display: block;
  width: 100%;
  color: #fff;
  font-size: 18px;
  height: 50px;
  line-height: 50px;
  border-radius: 5px;
  margin-top: 40px;
  background: #155AEF;
  border: 0;
  cursor: pointer;
}
.login-btn:hover {
  background: #004AEB;
}
.copy {
  position: fixed;
  bottom: 0;
  left: 0;
  width: 100%;
  padding: 20px 0;
  text-align: center;
  font-size: 14px;
  line-height: 1.5;
  color: #667085;
}
</style>
