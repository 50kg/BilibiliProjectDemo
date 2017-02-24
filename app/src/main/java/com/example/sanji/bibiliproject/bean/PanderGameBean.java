package com.example.sanji.bibiliproject.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sanji on 2017/2/21.
 */

public class PanderGameBean implements Serializable {

    /**
     * errno : 0
     * errmsg :
     * data : [{"cname":"英雄联盟","ename":"lol","img":"http://i9.pdim.gs/23e529ba353b33c2f70e6d60f6be4c29.jpeg","ext":"1000","status":"1","cdn_rate":"0"},{"cname":"守望先锋","ename":"overwatch","img":"http://i8.pdim.gs/70eb88bf67257964e012eacc97263f9f.png","ext":"1500","status":"1","cdn_rate":"0"},{"cname":"炉石传说","ename":"hearthstone","img":"http://i6.pdim.gs/18a15f74900b717ef5b77283ec8e0c37.png","ext":"2000","status":"1","cdn_rate":"0"},{"cname":"主机游戏","ename":"zhuji","img":"http://i7.pdim.gs/dedb172888351d04c6256e60f88f0c23.png","ext":"2980","status":"1","cdn_rate":"0"},{"cname":"黎明杀机","ename":"deadbydaylight","img":"http://i9.pdim.gs/ac9bfe540a489745c4f6334725ffa3d3.jpeg","ext":"2990","status":"1","cdn_rate":"0"},{"cname":"饥荒","ename":"starve","img":"http://i9.pdim.gs/962213bce2da73643f4dd3407ce183cf.jpeg","ext":"3000","status":"1","cdn_rate":"0"},{"cname":"DOTA2","ename":"dota2","img":"http://i5.pdim.gs/4795f1e933be01c4e7055e157c58aca0.png","ext":"3001","status":"1","cdn_rate":"0"},{"cname":"H1Z1","ename":"h1z1","img":"http://i8.pdim.gs/ef433db1e3994e882d5168dd7b99cc82.jpeg","ext":"3013","status":"1","cdn_rate":"0"},{"cname":"魔兽DOTA1","ename":"war3","img":"http://i7.pdim.gs/2c0e9577b5e15e8d5393aaf9c7956d08.jpeg","ext":"3250","status":"1","cdn_rate":"0"},{"cname":"DNF","ename":"dnf","img":"http://i7.pdim.gs/047467e36ad7fea589f987f5992638c8.png","ext":"3499","status":"1","cdn_rate":"0"},{"cname":"穿越火线","ename":"cf","img":"http://i7.pdim.gs/6d15c0f3da8914a30b27105670e6d62a.png","ext":"3500","status":"1","cdn_rate":"0"},{"cname":"魔兽世界","ename":"wow","img":"http://i5.pdim.gs/2d898309847b42f51e65e91c67596640.png","ext":"3500","status":"1","cdn_rate":"0"},{"cname":"CS:GO","ename":"csgo","img":"http://i7.pdim.gs/95748e44de319d47d7ebdf817928218d.png","ext":"3656","status":"1","cdn_rate":"0"},{"cname":"暗黑破坏神3","ename":"diablo3","img":"http://i9.pdim.gs/2f5967bf2d64cf9a864eb1088fc4e6b9.png","ext":"3665","status":"1","cdn_rate":"0"},{"cname":"风暴英雄","ename":"heroes","img":"http://i9.pdim.gs/c21e0383b1d324c4d3a55f01c0785a7e.png","ext":"3676","status":"1","cdn_rate":"0"},{"cname":"体育竞技","ename":"spg","img":"http://i5.pdim.gs/2f86239213ce4b00a78d2e5cf7295213.png","ext":"4025","status":"1","cdn_rate":"0"},{"cname":"我的世界","ename":"mc","img":"http://i9.pdim.gs/8deff4e452c6908621518321f6d944e3.png","ext":"4030","status":"1","cdn_rate":"0"},{"cname":"格斗游戏","ename":"ftg","img":"http://i5.pdim.gs/25b34e7976346010c855d65cf3b37ec9.png","ext":"4050","status":"1","cdn_rate":"0"},{"cname":"拳皇97","ename":"kof97","img":"http://i9.pdim.gs/12a9ecf556ff79fa81f78e588f7c5b3d.jpeg","ext":"4060","status":"1","cdn_rate":"0"},{"cname":"剑网3","ename":"jxol3","img":"http://i9.pdim.gs/905d8ccbd434a89adf404371ada5e929.jpeg","ext":"4490","status":"1","cdn_rate":"0"},{"cname":"天涯明月刀","ename":"tymyd","img":"http://i7.pdim.gs/22fb119d8c3e3917e1927ffdc0d54d37.png","ext":"4492","status":"1","cdn_rate":"0"},{"cname":"跑跑卡丁车","ename":"popkart","img":"http://i9.pdim.gs/595edb56afb784254121f499c594931c.jpeg","ext":"4493","status":"1","cdn_rate":"0"},{"cname":"传奇专区","ename":"legends","img":"http://i7.pdim.gs/08293e337689b44a7df632379d292ace.png","ext":"4494","status":"1","cdn_rate":"0"},{"cname":"怀旧经典","ename":"hjjd","img":"http://i8.pdim.gs/deacbc1bcecbf2494a75efa6d699f031.png","ext":"5018","status":"1","cdn_rate":"0"},{"cname":"精灵宝可梦","ename":"pokemon","img":"http://i6.pdim.gs/848d3abc94e97c6fd3c9f3438090aabf.jpeg","ext":"5030","status":"1","cdn_rate":"0"},{"cname":"流放之路","ename":"liufang","img":"http://i8.pdim.gs/bda2f1151e1a9730ac566e07db4c59ac.jpeg","ext":"5057","status":"1","cdn_rate":"0"},{"cname":"星际争霸2","ename":"starcraft","img":"http://i8.pdim.gs/bac22be920ca30206eb1a2037913e5a6.png","ext":"5058","status":"1","cdn_rate":"0"},{"cname":"网络游戏","ename":"wy","img":"http://i5.pdim.gs/e8b33c809f23d6bd767be39a4e9c60cd.jpeg","ext":"5080","status":"1","cdn_rate":"0"},{"cname":"战争游戏","ename":"shoot","img":"http://i7.pdim.gs/08b4c3d4eaf326f2cbb1c950b3c17adb.png","ext":"8000","status":"1","cdn_rate":"0"},{"cname":"王者荣耀","ename":"kingglory","img":"http://i6.pdim.gs/1288f555f436bbc11a4146e032a5ad4e.png","ext":"8010","status":"1","cdn_rate":"0"},{"cname":"仙境传说","ename":"ro","img":"http://i7.pdim.gs/b2db901f3337874b65f10e1606eaf769.jpeg","ext":"8012","status":"1","cdn_rate":"0"},{"cname":"阴阳师","ename":"yys","img":"http://i9.pdim.gs/1023198bb0c44b63e06f0ba80dd9ba5e.jpeg","ext":"8014","status":"1","cdn_rate":"0"},{"cname":"综合手游","ename":"mobilegame","img":"http://i6.pdim.gs/456901c1312c739e5f8802d95671be64.jpeg","ext":"8015","status":"1","cdn_rate":"0"},{"cname":"捕鱼天地","ename":"fishes","img":"http://i8.pdim.gs/a527d2849b207e822a86778ab72d3741.jpeg","ext":"8018","status":"1","cdn_rate":"0"},{"cname":"皇室战争","ename":"clashroyale","img":"http://i1.pdim.gs/t01dcf76a6f30fe40c9.jpg","ext":"8020","status":"1","cdn_rate":"0"},{"cname":"棋牌游戏","ename":"qipai","img":"http://i9.pdim.gs/7172f8f1093ab31311f0c92d21031618.png","ext":"8500","status":"1","cdn_rate":"0"}]
     * authseq :
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * cname : 英雄联盟
         * ename : lol
         * img : http://i9.pdim.gs/23e529ba353b33c2f70e6d60f6be4c29.jpeg
         * ext : 1000
         * status : 1
         * cdn_rate : 0
         */

        private String cname;
        private String ename;
        private String img;
        private String ext;
        private String status;
        private String cdn_rate;

        public String getCname() {
            return cname;
        }

        public void setCname(String cname) {
            this.cname = cname;
        }

        public String getEname() {
            return ename;
        }

        public void setEname(String ename) {
            this.ename = ename;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getExt() {
            return ext;
        }

        public void setExt(String ext) {
            this.ext = ext;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCdn_rate() {
            return cdn_rate;
        }

        public void setCdn_rate(String cdn_rate) {
            this.cdn_rate = cdn_rate;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "cname='" + cname + '\'' +
                    ", ename='" + ename + '\'' +
                    ", img='" + img + '\'' +
                    ", ext='" + ext + '\'' +
                    ", status='" + status + '\'' +
                    ", cdn_rate='" + cdn_rate + '\'' +
                    '}';
        }
    }
}
