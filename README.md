# RenderPlayerMod-1.8.9
ベース: https://github.com/SimplyRin/RenderPlayerMod-1.8.9

変更点  
・/renderplayer [right,left] から、/renderplayerに変え CPSModのような操作方法で好きな場所に設定できる  
・ベースではコンフィグを読み込んだりするコードが書かれてなかったから書き足した  


おかしな点   
・[ここ](https://goo.gl/LZfvxU)の変数のせいで位置変更する時にうまくドラッグできない (足辺りにしか判定がない)  
・位置変更の時にプレイヤーより手前に[ここ](https://goo.gl/6J88hm)が表示されちゃう (でもドラッグできるし、割とどうでもいい)  
・ベースのものにもあったけどHP以外のゲージのテクスチャが白くなっている (見ればわかる)  
