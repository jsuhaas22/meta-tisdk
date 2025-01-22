DESCRIPTION = "High Resolution OOB Demo"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

# Update SRC_URI to point to the GitHub repository
SRC_URI ="gitsm://github.com/TexasInstruments/lv_port_linux.git;branch=master;protocol=https \
          file://ti-lvgl-demo.service \
         "
#S = "${WORKDIR}/git/lv_port_linux"
S = "${WORKDIR}/git"

# SRCREV = "8d4fa43cd59b513d3d77e8e7523b5c5efdb5d8ac"
SRCREV = "6e3b8780f417400c689e146919682da0852afbb2"
#SRCREV_lvgl = "a6b62a10bc4472803ab2a24d39db15ad8f824847"

SYSTEMD_SERVICE:${PN} = "${PN}.service"
PV = "0.2"

DEPENDS += "alsa-lib"
RDEPENDS:${PN} = "alsa-lib"
DEPENDS += "mosquitto"
RDEPENDS:${PN} = "mosquitto"

inherit cmake
EXTRA_OECMAKE += " \
      -DCMAKE_CXX_FLAGS=-O3 \
      -DCMAKE_C_FLAGS=O3 \
      -DCMAKE_C_FLAGS=-I${STAGING_INCDIR}/libdrm \
      -DCMAKE_BUILD_TYPE=Release \
"

do_configure:prepend() {
    cmake -B build-arm64 -S ${S} \
    ${EXTRA_OECMAKE}
}

do_install() {
    CP_ARGS="-Prf --preserve=mode,timestamps --no-preserve=ownership"
    install -d ${D}${bindir}
    install -d ${D}${datadir}/ti-lvgl-demo/assets
    install -d ${D}${datadir}/ti-lvgl-demo/slides
    install -m 0755 ${S}/bin/lvglsim ${D}${bindir}
    cp ${CP_ARGS} ${S}/lvgl/demos/high_res/assets/* ${D}${datadir}/ti-lvgl-demo/assets
    cp ${CP_ARGS} ${S}/lvgl/demos/high_res/slides/* ${D}${datadir}/ti-lvgl-demo/slides

    install -d ${D}${systemd_system_unitdir}
    install -m 0755 ${WORKDIR}/ti-lvgl-demo.service ${D}${systemd_system_unitdir}/ti-lvgl-demo.service
}

FILES:${PN} += " \
    ${bindir}/lvglsim \
    ${bindir}/lvgl \
    ${datadir}/ti-lvgl-demo/assets \
    ${datadir}/ti-lvgl-demo/slides \
    ${systemd_system_unitdir}/ti-lvgl-demo.service \
"
