#!/bin/sh

case "$1" in
start)
    echo "Starting init script for loading SCULL module"
    if start-stop-daemon -S -x /usr/bin/scull_load; then
        echo "SCULL module loaded successfully"
    else
        echo "Failed to start SCULL module" >&2
        exit 1
    fi
    ;;
stop)
    echo "Removing user modules"
    if start-stop-daemon -K -x /usr/bin/scull_load; then
        echo "SCULL module process stopped"
    else
        echo "Failed to stop SCULL module" >&2
    fi
    if start-stop-daemon -S -x /usr/bin/scull_unload; then
        echo "SCULL module unloaded"
    else
        echo "Failed to unload SCULL module" >&2
        exit 1
    fi
    ;;
*)
    echo "Usage: $0 {start|stop}" >&2
    exit 1
    ;;
esac
exit 0
