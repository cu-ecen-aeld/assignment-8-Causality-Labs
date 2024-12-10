# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# The following license files were not able to be identified and are
# represented as "Unknown" below, you will need to check them yourself:
#   LICENSE
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f098732a73b5f6f3430472f5b094ffdb"

SRC_URI = "git://github.com/cu-ecen-aeld/assignment-7-Causality-Labs;protocol=https;branch=main \
           file://0001-Made-changes-to-makefile-and-hello.c.patch \
           file://0002-Moved-inlcude-files.patch \
           file://0003-Moved-include-directory-to-root.patch \
           file://0004-Fixed-entry-point-in-makefile.patch \
           file://0005-Added-EXPORT_SYMBOL-to-non-static-functions.patch \
           file://0006-Modified-misc-modules-makefile.patch \
           file://0007-Added-misc-modules-to-root-make-file.patch \
           file://module_load.sh \
           file://module_unload.sh \
           file://init.sh \
           "

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "5c3cae6ddc96b8645dfa6f6bc4ddbba08aae8789"

S = "${WORKDIR}/git"

inherit module

MODULES_MODULE_SYMVERS_LOCATION:append = "misc-modules"

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}/misc-modules"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

RPROVIDES:${PN} += "kernel-module-hello kernel-module-faulty"

inherit update-rc.d
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "misc-modules-start-stop"


FILES:${PN} += "${bindir}/* ${sysconfdir}/init.d/*"

do_install:append() {

	# Yocto variables ${D} and ${S} are useful here, which you can read about at 
	# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-D
	# and
	# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-S
	# See example at https://github.com/cu-ecen-aeld/ecen5013-yocto/blob/ecen5013-hello-world/meta-ecen5013/recipes-ecen5013/ecen5013-hello-world/ecen5013-hello-world_git.bb

    install -d ${D}${bindir} 
	install -m 0755 ${WORKDIR}/module_load.sh ${D}${bindir}/module_load
    install -m 0755 ${WORKDIR}/module_unload.sh ${D}${bindir}/module_unload

    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/init.sh ${D}${sysconfdir}/init.d/misc-modules-start-stop
}