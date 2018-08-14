<div id="table-of-contents">
<h2>Table of Contents</h2>
<div id="text-table-of-contents">
<ul>
<li><a href="#sec-1">1. 配置环境</a></li>
<li><a href="#sec-2">2. 发现的问题</a></li>
<li><a href="#sec-3">3. 解决步骤</a></li>
<li><a href="#sec-4">4. 安装texlive2018</a>
<ul>
<li><a href="#sec-4-1">4.1. 安装所需要的依赖包</a></li>
<li><a href="#sec-4-2">4.2. 获取texlive2018镜像</a></li>
<li><a href="#sec-4-3">4.3. 挂载镜像并安装</a></li>
<li><a href="#sec-4-4">4.4. 配置环境变量</a></li>
<li><a href="#sec-4-5">4.5. 卸载镜像</a></li>
<li><a href="#sec-4-6">4.6. 测试</a></li>
</ul>
</li>
<li><a href="#sec-5">5. 配置emacs的环境变量</a></li>
<li><a href="#sec-6">6. 结语</a></li>
</ul>
</div>
</div>

# 配置环境<a id="sec-1" name="sec-1"></a>

1.  Deepin15.6(Linux)
2.  emacs25.2

# 发现的问题<a id="sec-2" name="sec-2"></a>

1.  系统没有找到pdflatex命令，org-mode无法导出latex的pdf

# 解决步骤<a id="sec-3" name="sec-3"></a>

1.  安装texlive2018,因为pdflatex是texlive中的工具之一，texlive是tex的一个发行版
2.  在emacs中设置可执行路径(exec-path)，和环境变量(PATH)

# 安装texlive2018<a id="sec-4" name="sec-4"></a>

## 安装所需要的依赖包<a id="sec-4-1" name="sec-4-1"></a>

    sudo apt-get install libdigest-perl-md5-perl perl-tk

## 获取texlive2018镜像<a id="sec-4-2" name="sec-4-2"></a>

1.  [获取镜像地址](http://mirrors.ustc.edu.cn/CTAN/systems/texlive/Images/)

## 挂载镜像并安装<a id="sec-4-3" name="sec-4-3"></a>

    sudo mount -o loop texlive2018.iso /mnt/  
    cd /mnt/  
    sudo ./install-tl

提示选项下，输入“Ｉ”即可进行下载

## 配置环境变量<a id="sec-4-4" name="sec-4-4"></a>

1.  在 ~/.bash<sub>profile</sub> 下添加如下内容

    #tex live 2018
    export MANPATH=${MANPATH}:/usr/local/texlive/2018/texmf-dist/doc/man
    export INFOPATH=${INFOPATH}:/usr/local/texlive/2018/texmf-dist/doc/info
    export PATH=${PATH}:/usr/local/texlive/2018/bin/x86_64-linux/

1.  保存后， 执行命令

    source ~/.bash_profile

## 卸载镜像<a id="sec-4-5" name="sec-4-5"></a>

    sudo umount /mnt

## 测试<a id="sec-4-6" name="sec-4-6"></a>

test.latex:

    \documentclass{ctexart}
    \begin{document}
    $ \prod_{k=1}^n k=n! $
    \end{document}

输入命令进行测试：

    xelatex test.latex

打开对应的pdf文档查看即可

# 配置emacs的环境变量<a id="sec-5" name="sec-5"></a>

因为在Linux下，emacs不会加载zsh中的环境变量，因此需要需要设置“PATH”变量，才能通过“C-c C-e l p”键生成并导出latex的pdf文件，否则会提示错误，无法找到pdflatex文件
在“.emacs”文件下，或自己的配置文件下添加如下内容

    (setenv "PATH"
            (concat
             "/usr/local/texlive/2018/bin/x86_64-linux" ":"
             (getenv  "PATH")
             )
        )
    (let (
            (mypaths
             '(
               "/usr/local/texlive/2018/bin/x86_64-linux"
               ))
            )
        (setq exec-path (append exec-path mypaths) )
        )

其中，”exec-path“变量是可执行文件路径
此处具体可参考[设置环境变量与可执行文件路径](http://ergoemacs.org/emacs/emacs_env_var_paths.html)

# 结语<a id="sec-6" name="sec-6"></a>

1.  emacs真是很让人折腾，但是折腾成功之后，自信心也是满满的，希望自己坚持使用，坚持使用org-mode管理日常，写博客。
2.  参考文章
    -   [linux下安装texlive2017](https://blog.csdn.net/u010801696/article/details/78815514)
    -   [Emacs: Set Environment Variables within Emacs](http://ergoemacs.org/emacs/emacs_env_var_paths.html)