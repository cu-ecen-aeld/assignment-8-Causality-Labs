# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "git://github.com/cu-ecen-aeld/assignments-3-and-later-Ime-A;protocol=https;branch=main"

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "907ba61bd5958a324404a1c183e96939b475b9ce"
S = "${WORKDIR}/git/aesd-char-driver"

inherit module

EXTRA_OEMAKE = " -C ${STAGING_KERNEL_DIR} M=${S}"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

RPROVIDES:${PN} += "kernel-module-aesdchar"

inherit update-rc.d
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "aesdchar-start-stop"
INITSCRIPT_PARAMS:${PN} = "defaults 98"

FILES:${PN} += "${bindir}/* ${sysconfdir}/init.d/*"


do_install:append() {

    install -d ${D}${bindir} 
	install -m 0755 ${S}/aesdchar_load ${D}${bindir}/
    install -m 0755 ${S}/aesdchar_unload ${D}${bindir}/

    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${S}/aesd-char-driver-init ${D}${sysconfdir}/init.d/aesdchar-start-stop
 
}
