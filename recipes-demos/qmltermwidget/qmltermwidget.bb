# Recipe taken from https://github.com/webOS-ports/meta-webos-ports
# Commit: 528204c26b1550f54b7b93cbc9c32352ab5b0839
SUMMARY = "A terminal emulator QML widget, based on LXQt's QTermWidget"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=4641e94ec96f98fabc56ff9cc48be14b"

PV = "0.14.1+git"
SRCREV = "59f967d5e1f6e9ce8e1632d9405422b071d93d30"

DEPENDS = "qtbase qtdeclarative"
RDEPENDS:${PN} = "ttf-liberation-mono qtxmlpatterns"

SRC_URI = " \
    git://github.com/Swordfish90/qmltermwidget.git;protocol=https;branch=master \
    file://0001-qmltermwidget.pro-don-t-install-asset-directories-tw.patch \
"
S = "${WORKDIR}/git"

inherit ${@bb.utils.contains('BBLAYERS', 'meta-qt6', 'qt6-qmake', '', d)}

FILES:${PN} += "${libdir}"
