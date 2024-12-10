#!/bin/sh

case "$1" in
start)
    echo "Starting init script for loading hello and faulty modules"
    if start-stop-daemon -S -x /usr/bin/module_load -- hello; then
        echo "hello module loaded successfully"
    else
        echo "Failed to load hello module" >&2
        exit 1
    fi

    if start-stop-daemon -S -x /usr/bin/module_load -- faulty; then
        echo "faulty module loaded successfully"
    else
        echo "Failed to load faulty module" >&2
        exit 1
    fi
    ;;
stop)
    echo "Removing hello and faulty modules"
    if start-stop-daemon -K -x /usr/bin/module_load -- hello; then
        echo "hello module unloaded successfully"
    else
        echo "Failed to unload hello module" >&2
    fi

    if start-stop-daemon -K -x /usr/bin/module_unload -- faulty; then
        echo "faulty module unloaded successfully"
    else
        echo "Failed to unload faulty module" >&2
    fi
    ;;
*)
    echo "Usage: $0 {start|stop}" >&2
    exit 1
    ;;
esac
exit 0
